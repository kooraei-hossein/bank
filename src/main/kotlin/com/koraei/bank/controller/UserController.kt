package com.koraei.bank.controller

import com.koraei.bank.controller.request.CreateUserRequest
import com.koraei.bank.controller.request.UpdateUserRequest
import com.koraei.bank.service.UserService
import com.koraei.bank.service.dto.UserDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Rest Controller for managing users.
 *
 * Provides endpoints to handle user-related operations such as retrieving all users,
 * retrieving a user by ID, creating, updating, and deleting users.
 *
 * @param userService The service layer that handles the business logic for users.
 */
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    /**
     * Retrieves a list of all users.
     *
     * @return ResponseEntity containing a list of UserDto.
     */
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDto>> {
        val users = userService.findAll()
        return ResponseEntity.ok(users)
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity containing the UserDto of the requested user.
     */
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDto> {
        val user = userService.findById(id)
        return ResponseEntity.ok(user)
    }

    /**
     * Creates a new user.
     *
     * @param user The request body containing information for creating a new user.
     * @return ResponseEntity containing the created UserDto and an HTTP status of CREATED.
     */
    @PostMapping
    fun createUser(@RequestBody user: CreateUserRequest): ResponseEntity<UserDto> {
        val createdUser = userService.save(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }

    /**
     * Updates an existing user.
     *
     * @param id The ID of the user to update.
     * @param user The request body containing the updated user information.
     * @return ResponseEntity containing the updated UserDto.
     */
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody user: UpdateUserRequest): ResponseEntity<UserDto> {
        val updatedUser = userService.update(id, user)
        return ResponseEntity.ok(updatedUser)
    }

}

