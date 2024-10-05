package com.koraei.bank.controller

import com.koraei.bank.controller.request.CreateAccountRequest
import com.koraei.bank.controller.request.TransferRequest
import com.koraei.bank.controller.request.UpdateBalanceRequest
import com.koraei.bank.service.BankService
import com.koraei.bank.service.dto.BankAccountDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Rest Controller for handling bank account operations.
 *
 * Provides endpoints for creating accounts, depositing, withdrawing, transferring funds, and
 * checking account balances.
 *
 * @param bankService The service layer that handles the business logic related to banking operations.
 */
@RestController
@RequestMapping("/api/bank")
class BankController(private val bankService: BankService) {

    /**
     * Creates a new bank account.
     *
     * @param createAccountRequest The request body containing the necessary information to create a new account.
     * @return ResponseEntity containing the created BankAccountDto.
     */
    @PostMapping("/create")
    fun createAccount(@RequestBody createAccountRequest: CreateAccountRequest): ResponseEntity<BankAccountDto> {
        val account = bankService.createAccount(createAccountRequest)
        return ResponseEntity.ok(account)
    }

    /**
     * Deposits an amount into the specified bank account.
     *
     * @param accountNumber The number of the account to deposit into.
     * @param amount The amount to deposit.
     */
    @PostMapping("/deposit")
    fun deposit(@Valid @RequestBody request: UpdateBalanceRequest) =
        bankService.deposit(request)

    /**
     * Withdraws an amount from the specified bank account.
     *
     * @param accountNumber The number of the account to withdraw from.
     * @param amount The amount to withdraw.
     */
    @PostMapping("/withdraw")
    fun withdraw(@Valid @RequestBody request: UpdateBalanceRequest) =
        bankService.withdraw(request)

    /**
     * Transfers an amount from one account to another.
     *
     * @param fromAccount The account number to transfer from.
     * @param toAccount The account number to transfer to.
     * @param amount The amount to transfer.
     */
    @PostMapping("/transfer")
    fun transfer(
        @Valid @RequestBody request: TransferRequest
    ) = bankService.transfer(request)

    /**
     * Retrieves the balance of the specified bank account.
     *
     * @param accountNumber The number of the account to check the balance of.
     * @return The current balance of the account.
     */
    @GetMapping("/balance/{accountNumber}")
    fun getBalance(@PathVariable accountNumber: String) =
        bankService.getBalance(accountNumber)
}
