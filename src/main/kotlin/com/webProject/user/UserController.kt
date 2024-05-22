package com.webProject.user

import com.webProject.token.jwtToken.JwtTokenService
import com.webProject.user.model.request.UserDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/web/users")
class UserController(
    private val userService: UserService,
    @Value("\${web.user.jwt.key}") private val jwtKey: String,
    private val jwtTokenService: JwtTokenService,
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userDetail: UserDetail): ResponseEntity<Any> {
        return try {
            userService.registerUser(userDetail.name, userDetail.password)
            ResponseEntity.ok().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody userDetail: UserDetail): ResponseEntity<Any> {
        return try {
            val user = userService.loginUser(userDetail.name, userDetail.password)
            val token = jwtTokenService.createUserToken(user)

            val cookie = ResponseCookie.from(jwtKey, token)
                .httpOnly(true)
                .path("/")
                .build()
            ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}