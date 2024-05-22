package com.webProject.usersToken.model

import com.webProject.usersToken.model.response.ApiTokenDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "api_tokens")
class ApiToken {

    companion object {
        const val TOKEN_PREFIX = "API"
    }

    @Id
    @GeneratedValue
    var id: Int? = null

    @Column(unique = true, nullable = false)
    var name: String? = null

    @Column(nullable = false)
    var expireDate: Date? = null

    @Column(nullable = false)
    var token: UUID? = null


    fun toDto() = ApiTokenDto().also {
        it.token = "$TOKEN_PREFIX ${this.token}"
        it.name = this.name
        it.expireDate = this.expireDate
    }
}