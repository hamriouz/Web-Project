package com.webProject.admin

import com.webProject.user.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/web/admin")
class AdminController(
    private val adminService: AdminService,
    private val userRepository: UserRepository,
) {
    @PutMapping("/users/{username}/{active}")
    fun changeUserActivation(@PathVariable active: Boolean, @PathVariable username: String): ResponseEntity<Any> {
        val user = userRepository.findByName(username) ?: return ResponseEntity.notFound().build()
        user.active = active
        userRepository.save(user)
        return ResponseEntity.ok("User with name ${user.name}'s activation is now ${user.active}")
    }


    @GetMapping("/users")
    fun getUsers(): ResponseEntity<Any>{
        val users = adminService.getUsers()
        return ResponseEntity.ok(users)
    }
}