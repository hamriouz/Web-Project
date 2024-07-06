package com.webProject.user

import com.webProject.user.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Int> {

    override fun findAll(pageable: Pageable): Page<User>

    fun findByName(name: String): User?
}