package com.webProject

import com.webProject.user.UserRepository
import com.webProject.user.UserService
import com.webProject.user.model.User
import com.webProject.user.model.UserType
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Component
class Initializer(
    private val userRepository: UserRepository,
    private val userService: UserService,
    @Value("\${admin.username}")
    private val adminUsername: String,
    @Value("\${admin.password}")
    private val adminPassword: String,
    private val passwordEncoder: PasswordEncoder,

    ) {

    @EventListener(ApplicationReadyEvent::class)
    fun createAdmin() {
        var admin = userRepository.findByName(adminUsername)
        if (admin == null) {
            admin = User().apply {
                this.name = adminUsername
                this.active = true
                this.type = UserType.ADMIN
            }
            admin.password = adminPassword
            admin.encryptedPassword = passwordEncoder.encode(adminPassword)
            userRepository.save(admin)
        } else {
            val hashedPassword = userService.hashPassword(adminPassword)
            if (hashedPassword != admin.password) {
                admin.password = hashedPassword
                admin.encryptedPassword = passwordEncoder.encode(adminPassword)
                userRepository.save(admin)
            }
        }
    }

}