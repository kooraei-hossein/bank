package com.koraei.bank.repository

import com.koraei.bank.repository.entity.BankAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BankAccountRepository : JpaRepository<BankAccount, String> {
    fun findByAccountNumber(accountNumber: String): BankAccount?
}