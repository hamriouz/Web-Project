package com.webProject.usersToken

import com.webProject.usersToken.model.ApiToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ApiTokenRepository: CrudRepository<ApiToken, Int> {
//    interface ApiTokenRepository: EntityGraphJpaRepository<ApiToken, Int> {
    fun deleteByToken(token: UUID): Boolean
}