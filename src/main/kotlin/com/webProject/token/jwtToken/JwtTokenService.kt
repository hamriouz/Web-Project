package com.webProject.token.jwtToken

import com.webProject.user.model.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JwtTokenService(
    @Value("\${web.user.jwt.key}") private val jwtKey: String,
    @Value("\${web.user.jwt.access-token-expiration}") private val accessExpiration: Long,
    @Value("\${web.user.jwt.refresh-token-expiration}") private val refreshExpiration: String,
) {
    fun createUserToken(user: User): String {
        // todo complete
        return ""
    }
}