package com.webProject.admin

import com.webProject.user.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class AdminControllerImp(
    private val adminService: AdminService,
    private val userRepository: UserRepository,
): AdminController {
    override fun changeUserActivation(active: Boolean, username: String): ResponseEntity<Any> {
        val user = userRepository.findByName(username) ?: return ResponseEntity.notFound().build()
        user.active = active
        userRepository.save(user)
        return ResponseEntity.ok("User with name ${user.name}'s activation is now ${user.active}")
    }

    override fun getUsers(): ResponseEntity<Any> {
        val users = adminService.getUsers()
        return ResponseEntity.ok(users)
    }
}