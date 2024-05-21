package com.webProject.user

import com.webProject.user.model.request.UserDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/web/users")
interface UserController {
    @PostMapping("/register")
    fun registerUser(@RequestBody userDetail: UserDetail): ResponseEntity<Any>

    @PostMapping("/login")
    fun loginUser(@RequestBody userDetail: UserDetail): ResponseEntity<Any>

}