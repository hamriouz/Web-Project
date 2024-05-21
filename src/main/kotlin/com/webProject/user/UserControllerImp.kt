package com.webProject.user

import com.webProject.token.jwtToken.JwtTokenService
import com.webProject.user.model.request.UserDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller


@Controller
class UserControllerImp(
    private val userService: UserService,
    @Value("\${web.user.jwt.key}") private val jwtKey: String,
    private val jwtTokenService: JwtTokenService,
) : UserController {

    override fun registerUser(userDetail: UserDetail): ResponseEntity<Any> {
        userService.registerUser(userDetail.name, userDetail.password)
        return ResponseEntity.ok().build()
    }

    override fun loginUser(userDetail: UserDetail): ResponseEntity<Any> {
        val user = userService.loginUser(userDetail.name, userDetail.password)
        val token = jwtTokenService.createUserToken(user)

        val cookie = ResponseCookie.from(jwtKey, token)
            .httpOnly(true)
            .path("/")
            .build()
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build()
    }
}