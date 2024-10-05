package com.koraei.bank.repository

import com.koraei.bank.repository.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByIdCard(idCard: String): User?

}