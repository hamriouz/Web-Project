package com.webProject.user

import com.webProject.token.jwtToken.AuthenticationService
import com.webProject.token.jwtToken.JwtService
import com.webProject.user.model.User
import com.webProject.user.model.UserType
import com.webProject.user.model.response.LoginResponse
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationService: AuthenticationService,
    private val jwtService: JwtService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {
    fun registerUser(name: String, password: String) {
        var user = userRepository.findByName(name)
        if (user != null) {
            throw Exception("User with the given name already exists!")
        }
        user = User().apply {
            this.name = name
            this.password = passwordEncoder.encode(password)
            this.encryptedPassword = passwordEncoder.encode(password)
            this.active = false
            this.type = UserType.USER
        }
        user = userRepository.save(user)
    }

    fun loginUser(name: String, password: String): LoginResponse {
        val user = authenticationService.authenticate(name, password)
        val jwtToken = jwtService.generateToken(user)
        val loginResponse = LoginResponse().apply {
            this.token = jwtToken
            this.expiresIn = jwtService.getExpirationTime()
        }
        return loginResponse
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun hashPassword(password: String): String{
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(password.toByteArray())
        return digest.toHexString()
    }
}