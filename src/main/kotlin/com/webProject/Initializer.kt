package com.webProject

import com.webProject.user.UserRepository
import com.webProject.user.UserService
import com.webProject.user.model.User
import com.webProject.user.model.UserType
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component


@Component
class Initializer(
    private val userRepository: UserRepository,
    private val userService: UserService,
    @Value("\${admin.username}")
    private val adminUsername: String,
    @Value("\${admin.password}")
    private val adminPassword: String,

) {

    @EventListener(ApplicationReadyEvent::class)
    fun createAdmin() {
        var admin = userRepository.findByName(adminUsername)
        if (admin == null) {
            admin = User().apply {
                this.name = adminUsername
                this.password = userService.hashPassword(adminPassword)
                this.active = true
                this.type = UserType.ADMIN
            }
            userRepository.save(admin)
        } else {
            val hashedPassword = userService.hashPassword(adminPassword)
            if (hashedPassword != admin.password) {
                admin.password = hashedPassword
                userRepository.save(admin)
            }
        }
    }

}