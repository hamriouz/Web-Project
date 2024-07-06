package com.webProject.usersToken

import com.webProject.user.UserRepository
import com.webProject.user.model.User
import com.webProject.usersToken.model.ApiToken
import com.webProject.usersToken.model.response.ApiTokenDto
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsersService(
    private val apiTokenRepository: ApiTokenRepository,
    private val userRepository: UserRepository,
) {
    fun createUserApiToken(name: String, expireDate: Date, username: String): ApiTokenDto {
        val user = userRepository.findByName(username)
        val token = UUID.randomUUID()
        var apiToken = ApiToken().apply {
            this.name = name
            this.token = token
            this.expireDate = expireDate
            this.user = user
        }
        apiToken = apiTokenRepository.save(apiToken)
        return apiToken.toDto()
    }

    fun deleteUserApiToken(deleteToken: UUID): Boolean {
        val token = apiTokenRepository.findByToken(deleteToken)
         if (token != null) {
             apiTokenRepository.delete(token)
             return true
         }
        return false
    }
}