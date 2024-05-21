package com.webProject.user

import com.webProject.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Int> {

    fun findByName(name: String): User?
}