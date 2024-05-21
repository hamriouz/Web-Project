package com.webProject.usersToken

import com.webProject.usersToken.model.request.CreateApiTokenRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody


@RestController
@RequestMapping("/api/web/user")
interface UsersController {
    @PostMapping("/api-tokens")
    fun createApiToken(@RequestBody createApiToken: CreateApiTokenRequest): ResponseEntity<Any>

    @GetMapping("/api-tokens")
    fun getApiToken(): ResponseEntity<Any>

    @DeleteMapping("/api-tokens")
    fun deleteApiTokens(@RequestBody deleteToken: String): ResponseEntity<Any>

}