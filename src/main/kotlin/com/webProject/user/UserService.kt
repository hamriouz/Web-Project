package com.webProject.user

import com.webProject.user.model.User
import com.webProject.user.model.UserType
import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun registerUser(name: String, password: String) {
        val user = User().apply {
            this.name = name
            this.password = hashPassword(password)
            this.active = false
            this.type = UserType.USER
        }
        userRepository.save(user)
    }

    fun loginUser(name: String, password: String): User {
        val hashedPassword = hashPassword(password)
        val user = userRepository.findByName(name)
        if (user == null || user.password != hashedPassword)
            throw Exception("Incorrect username or password")
        if (user.active == false)
            throw Exception("User is not active")
        return user
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun hashPassword(password: String): String{
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(password.toByteArray())
        return digest.toHexString()
    }
}