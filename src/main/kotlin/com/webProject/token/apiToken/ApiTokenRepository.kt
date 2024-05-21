package com.webProject.token.apiToken

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository
import com.webProject.token.apiToken.model.ApiToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ApiTokenRepository: CrudRepository<ApiToken, Int> {
//    interface ApiTokenRepository: EntityGraphJpaRepository<ApiToken, Int> {

    fun findByToken(token: String): ApiToken
}