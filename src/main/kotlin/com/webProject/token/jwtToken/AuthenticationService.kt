package com.webProject.token.jwtToken

import com.webProject.user.UserRepository
import com.webProject.user.model.User
import com.webProject.user.model.request.UserDetail
import org.hibernate.annotations.NotFound
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
) {
    fun authenticate(name: String, password: String): User {
        val user =  userRepository.findByName(name)
        if (user != null && passwordEncoder.matches(password, user.encryptedPassword)) {
            return user
        }
        throw IllegalStateException("User not found")
    }
}