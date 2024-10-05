package com.koraei.bank.repository.entity

import com.koraei.bank.service.dto.UserDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * Entity class representing a user in the system.
 *
 * This class is mapped to the "users" table in the database and contains fields
 * such as first name, last name, phone number, and ID card. The ID card is
 * unique for each user.
 *
 * @param id The unique identifier for the user (auto-generated).
 * @param createAt The timestamp of when the user was created.
 * @param firstName The user's first name.
 * @param lastName The user's last name.
 * @param phone The user's phone number.
 * @param idCard The user's unique ID card number.
 */
@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,

    @Column(nullable = false)
    var createAt: LocalDateTime,

    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String,

    @Column(nullable = false)
    var phone: String,

    @Column(unique = true, nullable = false)
    var idCard: String

) {

    /**
     * Default constructor for Hibernate.
     */
    constructor() : this(0, LocalDateTime.now(), "", "", "", "")

    /**
     * Converts the User entity to a UserDto.
     *
     * @return A UserDto containing the user's details.
     */
    fun toDto(): UserDto {
        return UserDto(
            id = id,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            idCard = idCard
        )
    }
}
