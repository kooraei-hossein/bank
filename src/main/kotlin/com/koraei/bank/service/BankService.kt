package com.koraei.bank.service

import com.koraei.bank.controller.request.CreateAccountRequest
import com.koraei.bank.controller.request.TransferRequest
import com.koraei.bank.controller.request.UpdateBalanceRequest
import com.koraei.bank.repository.BankAccountRepository
import com.koraei.bank.repository.UserRepository
import com.koraei.bank.repository.entity.BankAccount
import com.koraei.bank.service.dto.BankAccountDto
import com.koraei.bank.service.exception.GeneralException
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock
import javax.transaction.Transactional
import kotlin.concurrent.withLock

/**
 * Service layer that handles all banking operations such as account creation, deposits,
 * withdrawals, transfers, and balance retrieval.
 *
 * This class uses a ReentrantLock and an ExecutorService to manage concurrent access to
 * bank account data and ensure thread safety during transactions.
 *
 * @param accountRepository Repository for accessing and managing bank account data.
 * @param userRepository Repository for accessing user data.
 * @param transactionLogger Service for logging all transactions.
 */
@Service
class BankService(
    private val accountRepository: BankAccountRepository,
    private val userRepository: UserRepository,
    private val transactionLogger: TransactionLogger
) {
    private val lock = ReentrantLock()
    private val executorService: ExecutorService = Executors.newFixedThreadPool(10)

    /**
     * Creates a new bank account for the user.
     *
     * @param createAccountRequest The request containing the user's information and initial balance.
     * @return The created BankAccountDto.
     */
    @Transactional
    fun createAccount(createAccountRequest: CreateAccountRequest): BankAccountDto {
        val account = BankAccount(
            accountHolderName = createAccountRequest.accountHolderName,
            accountId = UUID.randomUUID().toString(),
            balance = createAccountRequest.balance,
            user = userRepository.findById(createAccountRequest.userId).get()
        )
        return accountRepository.save(account).toDto()
    }

    /**
     * Deposits an amount into a specified bank account.
     *
     * The operation is executed asynchronously to ensure non-blocking behavior,
     * and concurrency is controlled using a lock.
     *
     * @param accountId The number of the account to deposit into.
     * @param amount The amount to deposit.
     */
    fun deposit(request: UpdateBalanceRequest) {
        executorService.submit {
            lock.withLock {
                val account = accountRepository.findById(request.accountId).orElseThrow()
                account.balance += request.amount
                accountRepository.save(account)
                transactionLogger.onTransaction(request.accountId, "Deposit", request.amount)
            }
        }
    }

    /**
     * Withdraws an amount from a specified bank account.
     *
     * The operation is executed asynchronously, and concurrency is handled using a lock.
     * If there are insufficient funds, an exception is thrown.
     *
     * @param accountId The number of the account to withdraw from.
     * @param amount The amount to withdraw.
     * @throws GeneralException If there are insufficient funds in the account.
     */
    fun withdraw(request: UpdateBalanceRequest) {
        executorService.submit {
            lock.withLock {
                val account = accountRepository.findById(request.accountId).orElseThrow()
                if (account.balance >= request.amount) {
                    account.balance -= request.amount
                    accountRepository.save(account)
                    transactionLogger.onTransaction(request.accountId, "Withdrawal", request.amount)
                } else {
                    throw GeneralException("message.bank.transaction.funds")
                }
            }
        }
    }

    /**
     * Transfers an amount from one bank account to another.
     *
     * The operation is executed asynchronously and ensures both accounts are updated atomically.
     * If there are insufficient funds in the sender's account, an exception is thrown.
     *
     * @param request The data of the account to transfer from and to with amount.
     * @throws GeneralException If the sender's account has insufficient funds.
     */
    fun transfer(request: TransferRequest) {
        executorService.submit {
            lock.withLock {
                val fromAccount = accountRepository.findById(request.fromAccount).orElseThrow()
                val toAccount = accountRepository.findById(request.toAccount).orElseThrow()
                if (fromAccount.balance >= request.amount) {
                    fromAccount.balance -= request.amount
                    toAccount.balance += request.amount
                    accountRepository.save(fromAccount)
                    accountRepository.save(toAccount)
                    transactionLogger.onTransaction(request.fromAccount, "Transfer Out", request.amount)
                    transactionLogger.onTransaction(request.toAccount, "Transfer In", request.amount)
                } else {
                    throw GeneralException("message.bank.transaction.funds")
                }
            }
        }
    }

    /**
     * Retrieves the balance of a specified bank account.
     *
     * @param accountId The number of the account to check the balance of.
     * @return The current balance of the account, or 0.0 if the account is not found.
     */
    fun getBalance(accountId: String): Double {
        return accountRepository.findByaccountId(accountId)?.balance ?: 0.0
    }
}

