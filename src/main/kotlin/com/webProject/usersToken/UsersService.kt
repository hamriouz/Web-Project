package com.webProject.usersToken

import com.webProject.usersToken.model.ApiToken
import com.webProject.usersToken.model.response.ApiTokenDto
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsersService(
    private val apiTokenRepository: ApiTokenRepository
) {
    fun createUserApiToken(name: String, expireDate: Date): ApiTokenDto {
        val token = UUID.randomUUID()
        var apiToken = ApiToken().apply {
            this.name = name
            this.token = token
            this.expireDate = expireDate
        }
        apiToken = apiTokenRepository.save(apiToken)
        return apiToken.toDto()
    }

    fun deleteUserApiToken(deleteToken: UUID): Boolean {
        return apiTokenRepository.deleteByToken(deleteToken)
    }
}