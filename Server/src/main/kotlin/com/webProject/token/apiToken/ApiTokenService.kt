package com.webProject.token.apiToken

import com.webProject.usersToken.ApiTokenRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ApiTokenService(
    private val apiTokenRepository: ApiTokenRepository,
) {
    fun extractUsername(token: UUID): String? {
        val apiToken = apiTokenRepository.findByToken(token)
        return apiToken.user!!.username
    }

    fun isTokenValid(token: String): Boolean {
        val apiToken = apiTokenRepository.findByToken(UUID.fromString(token))
        return !(apiToken.expireDate)!!.before(Date())
    }
}