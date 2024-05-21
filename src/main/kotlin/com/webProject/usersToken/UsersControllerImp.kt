package com.webProject.usersToken

import com.webProject.token.apiToken.ApiTokenRepository
import com.webProject.token.apiToken.model.ApiToken
import com.webProject.usersToken.model.ApiTokenDto
import com.webProject.usersToken.model.request.CreateApiTokenRequest
import com.webProject.usersToken.model.response.DeleteApiTokenResponse
import com.webProject.usersToken.model.response.GetApiTokensResponse
import org.springframework.http.ResponseEntity

class UsersControllerImp(
    private val usersService: UsersService,
    private val apiTokenRepository: ApiTokenRepository,
): UsersController {
    override fun createApiToken(createApiToken: CreateApiTokenRequest): ResponseEntity<Any> {
        val token = usersService.createUserApiToken(createApiToken.name!!, createApiToken.expireDate!!)
        var apiToken = ApiToken().apply {
            this.name = createApiToken.name
            this.token = token
            this.expirationDate = createApiToken.expireDate
        }
        apiToken = apiTokenRepository.save(apiToken)
        return ResponseEntity.ok(apiToken)
    }

    override fun deleteApiTokens(deleteToken: String): ResponseEntity<Any> {
        val token = apiTokenRepository.findByToken(deleteToken) ?: return ResponseEntity.notFound().build()
        apiTokenRepository.delete(token)
        val deleteResponse = DeleteApiTokenResponse().apply { this.deleted = true }
        return ResponseEntity.ok(deleteResponse)
    }

    override fun getApiToken(): ResponseEntity<Any> {
//        todo add pagination
        apiTokenRepository.findAll()
        val apiTokens = apiTokenRepository.findAll().toList()
        val tokenResponse = getApiTokensDto(apiTokens)
        return ResponseEntity.ok(tokenResponse)
    }

    private fun getApiTokensDto(apiTokens: List<ApiToken>): GetApiTokensResponse {
        val tokens = mutableListOf<ApiTokenDto>()
        apiTokens.forEach{ apiToken ->
            val token = ApiTokenDto().apply {
                this.token = "API ***"
                this.expireDate = apiToken.expirationDate
                this.name = apiToken.name
            }
            tokens.add(token)
        }
        return GetApiTokensResponse().apply {
            this.count = apiTokens.size
            this.tokens = tokens
        }
    }
}