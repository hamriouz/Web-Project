package com.webProject.user

//import com.webProject.token.jwtToken.JwtTokenService
import com.webProject.user.model.request.UserDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.math.log


@RestController
@RequestMapping("/api/web/users")
class UserController(
    private val userService: UserService,
    @Value("\${web.user.jwt.key}") private val jwtKey: String,
//    private val jwtTokenService: JwtTokenService,
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
            val loginResponse = userService.loginUser(userDetail.name, userDetail.password)
            ResponseEntity.ok(loginResponse)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}