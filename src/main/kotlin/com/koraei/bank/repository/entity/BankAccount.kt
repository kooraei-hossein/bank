package com.koraei.bank.repository.entity

import com.koraei.bank.service.dto.BankAccountDto
import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Entity class representing a bank account in the system.
 *
 * This class is mapped to the "bank_account" table in the database and contains fields
 * such as the account number, account holder's name, balance, and a reference to the associated user.
 *
 * @param accountId The unique identifier for the bank account.
 * @param updatedAt The timestamp of the last update to the account.
 * @param accountHolderName The name of the account holder.
 * @param balance The current balance of the account.
 * @param user The user associated with this bank account.
 */
@Entity
@Table(name = "bank_account")
data class BankAccount(

    @Id
    var accountId: String,

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var accountHolderName: String,

    @Column(nullable = false)
    var balance: Double,

    @ManyToOne
    var user: User
) {
    /**
     * Default constructor for Hibernate.
     */
    constructor() : this("", LocalDateTime.now(), "", 0.0, User())

    /**
     * Converts the BankAccount entity to a BankAccountDto.
     *
     * @return A BankAccountDto containing the bank account's details.
     */
    fun toDto(): BankAccountDto {
        return BankAccountDto(
            accountId = accountId,
            accountHolderName = accountHolderName,
            balance = balance,
            user = user.toDto()
        )
    }
}
