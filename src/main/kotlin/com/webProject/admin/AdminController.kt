package com.webProject.admin

import com.webProject.common.GetPageRequest
import com.webProject.user.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/web/admin")
class AdminController(
    private val userRepository: UserRepository,
) {
    @PutMapping("/users/{username}/{active}")
    fun changeUserActivation(@PathVariable active: Boolean, @PathVariable username: String): ResponseEntity<Any> {
        val user = userRepository.findByName(username) ?: return ResponseEntity.notFound().build()
        user.active = active
        userRepository.save(user)
        return ResponseEntity.ok("User with name ${user.name}'s activation is now ${user.active}")
    }


    @PostMapping("/users")
    fun getUsers(@RequestBody pageRequest: GetPageRequest): ResponseEntity<Any>{
        val pageable: Pageable = PageRequest.of(pageRequest.page!!, pageRequest.size!!)
        val users = userRepository.findAll(pageable)
        return ResponseEntity.ok(users)
    }
}