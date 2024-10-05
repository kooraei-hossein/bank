package com.koraei.bank.service

import com.koraei.bank.controller.request.CreateUserRequest
import com.koraei.bank.controller.request.UpdateUserRequest
import com.koraei.bank.repository.UserRepository
import com.koraei.bank.repository.entity.User
import com.koraei.bank.service.dto.UserDto
import com.koraei.bank.service.exception.GeneralException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * Service layer for managing users.
 *
 * This class provides methods for retrieving, creating, updating, and deleting users.
 * It includes validation logic to handle scenarios like duplicate ID cards and
 * missing users.
 *
 * @param userRepository Repository for accessing and managing user data.
 */
@Service
class UserService(private val userRepository: UserRepository) {

    /**
     * Retrieves a list of all users in the system.
     *
     * @return A list of UserDto representing all users.
     */
    fun findAll(): List<UserDto> {
        return userRepository.findAll().mapNotNull { it.toDto() }
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The UserDto representing the user.
     * @throws GeneralException If the user is not found.
     */
    fun findById(id: Long): UserDto {
        val user = userRepository.findById(id).orElseThrow { GeneralException("message.bank.user.not_found") }
        return user.toDto()
    }

    /**
     * Creates a new user.
     *
     * This method checks for duplication by ID card before saving the user.
     *
     * @param request The CreateUserRequest containing the user's details.
     * @return The created UserDto.
     * @throws GeneralException If a user with the same ID card already exists.
     */
    fun save(request: CreateUserRequest): UserDto {
        if (userRepository.findByIdCard(request.idCard) != null) {
            throw GeneralException("message.bank.user.duplication")
        }

        val user = User(
            createAt = LocalDateTime.now(),
            firstName = request.firstName,
            lastName = request.lastName,
            phone = request.phone,
            idCard = request.idCard,
        )
        return userRepository.save(user).toDto()
    }

    /**
     * Updates an existing user.
     *
     * This method checks if the user exists before applying the updates.
     *
     * @param id The ID of the user to update.
     * @param updatedUser The UpdateUserRequest containing the new user details.
     * @return The updated UserDto.
     * @throws GeneralException If the user is not found.
     */
    fun update(id: Long, updatedUser: UpdateUserRequest): UserDto {
        val user = userRepository.findById(id)
        if (user.isEmpty) {
            throw GeneralException("message.bank.user.not_found")
        }
        user.get().apply {
            firstName = updatedUser.firstName
            lastName = updatedUser.lastName
            phone = updatedUser.phone
        }
        return userRepository.save(user.get()).toDto()
    }

}

