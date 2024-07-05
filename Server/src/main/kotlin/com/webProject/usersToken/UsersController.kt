package com.webProject.usersToken

import com.webProject.token.jwtToken.AuthenticationService
import com.webProject.user.UserRepository
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
@CrossOrigin(origins = ["http://localhost:3000"], allowCredentials = "true")
@RequestMapping("/api/web/user")
class UsersController(
    private val usersService: UsersService,
    private val apiTokenRepository: ApiTokenRepository,
    private val authenticationService: AuthenticationService,
    private val userRepository: UserRepository,
) {

    @PostMapping("/api-tokens")
    fun createApiToken(@RequestBody createApiToken: CreateApiTokenRequest): ResponseEntity<ApiTokenDto> {
        val user = authenticationService.getCurrentUserDto()
        val apiToken = usersService.createUserApiToken(createApiToken.name, createApiToken.expireDate, user!!.name!!)
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
        val canBeDeleted = usersService.deleteUserApiToken(deleteTokenUuid)
        val deleteResponse = DeleteApiTokenResponse().also { it.deleted = canBeDeleted }
        return ResponseEntity.ok(deleteResponse)
    }

    @DeleteMapping("/api-tokens/{tokenName}")
    fun deleteApiTokenByName(@PathVariable tokenName: String): ResponseEntity<String> {
        val apiToken = apiTokenRepository.findByName(tokenName)
        apiTokenRepository.delete(apiToken)
        return ResponseEntity.ok("token with name $tokenName was deleted successfully")
    }

    @GetMapping("/api-tokens/{username}")
    fun getApiToken(@RequestParam(required = true) page: Int,
                    @RequestParam(required = true) size: Int,
                    @PathVariable username: String,
    ): ResponseEntity<Any> {
        val user = userRepository.findByName(username)
        val pageable: Pageable = PageRequest.of(page, size)
        val apiTokens = apiTokenRepository.findAllByUser(user!!, pageable)
        val totalSize = apiTokenRepository.findAllByUser(user).size
        val tokenResponse = getApiTokensDto(apiTokens)
        tokenResponse.totalPageSize = getTotalPagesSize(totalSize, size)
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

    private fun getTotalPagesSize(totalSize: Int, pageSize: Int): Int {
        val result = totalSize.toDouble() / pageSize.toDouble()
        if (result - result.toInt() > 0) {
            return result.toInt() + 1
        }
        return result.toInt()
    }
}