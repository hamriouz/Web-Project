package com.webProject.usersToken

import com.webProject.common.GetPageRequest
import com.webProject.usersToken.model.ApiToken
import com.webProject.usersToken.model.response.ApiTokenDto
import com.webProject.usersToken.model.request.CreateApiTokenRequest
import com.webProject.usersToken.model.response.DeleteApiTokenResponse
import com.webProject.usersToken.model.response.GetApiTokensResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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
            this.token = apiToken.token.toString()
            this.name = apiToken.name
            this.expireDate = apiToken.expireDate
        }
        return ResponseEntity.ok(apiTokenResponse)
    }

    @DeleteMapping("/api-tokens")
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

    @GetMapping("/api-tokens")
    fun getApiToken(@RequestParam(required = true) page: Int, @RequestParam(required = true) size: Int): ResponseEntity<Any> {
        val pageable: Pageable = PageRequest.of(page, size)
        val apiTokens = apiTokenRepository.findAll(pageable)
        val tokenResponse = getApiTokensDto(apiTokens)
        return ResponseEntity.ok(tokenResponse)
    }

    private fun getApiTokensDto(apiTokens: Page<ApiToken>): GetApiTokensResponse {
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