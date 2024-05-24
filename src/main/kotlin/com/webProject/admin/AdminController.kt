package com.webProject.admin

import com.webProject.user.UserRepository
import com.webProject.user.model.User
import com.webProject.user.model.request.FullUserDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/web/admin")
class AdminController(
    private val userRepository: UserRepository,
) {
    @PutMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    fun changeUserActivation(
        @RequestParam(required = true) active: Boolean,
        @RequestParam(required = true) username: String
    ): ResponseEntity<Any> {
        val user = userRepository.findByName(username) ?: return ResponseEntity.notFound().build()
        user.active = active
        userRepository.save(user)
        return ResponseEntity.ok("User with name ${user.name}'s activation is now ${user.active}")
    }


    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    fun getUsers(
        @RequestParam(required = true) page: Int,
        @RequestParam(required = true) size: Int
    ): ResponseEntity<Any> {
        val pageable: Pageable = PageRequest.of(page, size)
        val users = getUsersDto(userRepository.findAll(pageable))
        val pages: Page<FullUserDto> = PageImpl(users, pageable, users.size.toLong())

        return ResponseEntity.ok(pages)
    }

    private fun getUsersDto(users: Page<User>): List<FullUserDto> {
        val result = mutableListOf<FullUserDto>()
        users.forEach { user ->
            val dto = FullUserDto().apply {
                this.name = user.name
                this.active = user.active
                this.type = user.type
            }
            result.add(dto)
        }
        return result
    }
}