package com.webProject.token.jwtToken

import com.webProject.user.UserRepository
import com.webProject.user.model.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun authenticate(name: String, password: String): User {
        val user =  userRepository.findByName(name)
        if (user != null && passwordEncoder.matches(password, user.encryptedPassword)) {
            return user
        }
        throw IllegalStateException("User not found")
    }

    fun getCurrentUserDto(): User? {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        val isAuthenticated = authentication != null && authentication.principal is User
        return if (isAuthenticated) authentication!!.principal as User else null
    }
}