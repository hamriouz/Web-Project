package com.webProject.usersToken

import com.webProject.usersToken.model.ApiToken
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ApiTokenRepository: JpaRepository<ApiToken, Int> {

    override fun findAll(pageable: Pageable): Page<ApiToken>

    fun deleteByToken(token: UUID): Boolean

    fun findByName(name: String): ApiToken
}