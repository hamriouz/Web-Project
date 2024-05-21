package com.webProject.admin

import com.webProject.user.model.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/web/admin")
interface AdminController {
    @PutMapping("/users/{username}/{active}")
    fun changeUserActivation(@PathVariable active: Boolean, @PathVariable username: String): ResponseEntity<Any>

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<Any>

}