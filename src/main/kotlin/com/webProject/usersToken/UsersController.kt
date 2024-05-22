package com.webProject.usersToken

import com.webProject.usersToken.model.ApiToken
import com.webProject.usersToken.model.response.ApiTokenDto
import com.webProject.usersToken.model.request.CreateApiTokenRequest
import com.webProject.usersToken.model.response.DeleteApiTokenResponse
import com.webProject.usersToken.model.response.GetApiTokensResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.UUID

@RestController
@RequestMapping("/api/web/user")
class UsersController(
    private val usersService: UsersService,
    private val apiTokenRepository: ApiTokenRepository,
) {

    @PostMapping("/api-tokens")
    fun createApiToken(@RequestBody createApiToken: CreateApiTokenRequest): ResponseEntity<ApiTokenDto> {
        val apiToken = usersService.createUserApiToken(createApiToken.name, createApiToken.expireDate)
        val apiTokenResponse = ApiTokenDto().apply {
            this.token = "API " + apiToken.token.toString()
            this.name = apiToken.name
            this.expireDate = apiToken.expireDate
        }
        return ResponseEntity.ok(apiTokenResponse)
    }

    @GetMapping("/api-tokens")
    fun deleteApiTokens(@RequestHeader("Authorization") deleteToken: String): ResponseEntity<DeleteApiTokenResponse> {
        val deleteTokenSplit = deleteToken.split(" ")
        if (deleteTokenSplit.size != 2) {
            return ResponseEntity.badRequest().build()
        }
        if (deleteTokenSplit[0] != ApiToken.TOKEN_PREFIX) {
            return ResponseEntity.badRequest().build()
        }
        val deleteTokenUuid = try {
            UUID.fromString(deleteTokenSplit[1])
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().build()
        }

        val deleted = usersService.deleteUserApiToken(deleteTokenUuid)
        val deleteResponse = DeleteApiTokenResponse().also { it.deleted = deleted }
        return ResponseEntity.ok(deleteResponse)
    }

    @DeleteMapping("/api-tokens")
    fun getApiToken(): ResponseEntity<Any> {
//        todo add pagination
        apiTokenRepository.findAll()
        val apiTokens = apiTokenRepository.findAll().toList()
        val tokenResponse = getApiTokensDto(apiTokens)
        return ResponseEntity.ok(tokenResponse)
    }

    private fun getApiTokensDto(apiTokens: List<ApiToken>): GetApiTokensResponse {
        val tokens = mutableListOf<ApiTokenDto>()
        apiTokens.forEach { apiToken ->
            val token = ApiTokenDto().apply {
                this.token = "API ***"
                this.expireDate = apiToken.expireDate
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