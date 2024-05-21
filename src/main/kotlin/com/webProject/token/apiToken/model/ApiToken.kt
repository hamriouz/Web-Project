package com.webProject.token.apiToken.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.Date

@Entity
@Table(name = "api_tokens")
class ApiToken {
    @Id
    @GeneratedValue
    var id: Int? = null

    @Column(unique = true, nullable = false)
    var name: String? = null

    @Column(nullable = false)
    var expirationDate: Date? = null

    @Column(nullable = false)
    var token: String? = null


}