package com.webProject.admin

import com.webProject.admin.model.GetUsersResponse
import com.webProject.user.UserRepository
import com.webProject.user.model.User
import com.webProject.user.model.request.FullUserDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["http://localhost:3000"], allowCredentials = "true")
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
    @CrossOrigin(origins = ["http://localhost:3000"], allowCredentials = "true")
    fun getUsers(
        @RequestParam(required = true) page: Int,
        @RequestParam(required = true) size: Int
    ): ResponseEntity<Any> {
        val pageable: Pageable = PageRequest.of(page, size)
        val users = userRepository.findAll(pageable)
        val usersSize = userRepository.findAll().size
        val userResponse = getUsersDto(users)
        userResponse.totalPageSize = getTotalPagesSize(usersSize, size)
        return ResponseEntity.ok(userResponse)
    }

    private fun getUsersDto(users: Page<User>): GetUsersResponse {
        val result = mutableListOf<FullUserDto>()
        users.forEach { user ->
            val dto = FullUserDto().apply {
                this.name = user.name
                this.active = user.active
                this.type = user.type
                this.createdDate = user.createdDate
            }
            result.add(dto)
        }
        return GetUsersResponse().apply {
            this.count = users.size
            this.users = result
        }
    }

    private fun getTotalPagesSize(totalSize: Int, pageSize: Int): Int {
        val result = totalSize.toDouble() / pageSize.toDouble()
        if (result - result.toInt() > 0) {
            return result.toInt() + 1
        }
        return result.toInt()
    }
}