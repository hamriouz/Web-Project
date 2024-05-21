//package com.webProject.token.jwtToken
//
//import com.webProject.token.jwtToken.data.JwtProperties
//import com.webProject.user.model.request.UserDetail
//import io.jsonwebtoken.Claims
//import io.jsonwebtoken.Jwts
//import org.springframework.stereotype.Service
//import java.util.*
//
//@Service
//class TokenService(
//    jwtProperties: JwtProperties
//) {
//
//    private val secretKey = Keys.hmacShaKeyFor(
//        jwtProperties.key.toByteArray()
//    )
//
//    fun generate(
//        userDetails: UserDetails,
//        expirationDate: Date,
//        additionalClaims: Map<String, Any> = emptyMap()
//    ): String =
//        Jwts.builder()
//            .claims()
//            .subject(userDetails.username)
//            .issuedAt(Date(System.currentTimeMillis()))
//            .expiration(expirationDate)
//            .add(additionalClaims)
//            .and()
//            .signWith(secretKey)
//            .compact()
//
//    fun isValid(token: String, userDetails: UserDetail): Boolean {
//        val email = extractEmail(token)
//
//        return userDetails.name == name && !isExpired(token)
//    }
//
//    fun extractEmail(token: String): String? =
//        getAllClaims(token)
//            .subject
//
//    fun isExpired(token: String): Boolean =
//        getAllClaims(token)
//            .expiration
//            .before(Date(System.currentTimeMillis()))
//
//    private fun getAllClaims(token: String): Claims {
//        val parser = Jwts.parser()
//            .verifyWith(secretKey)
//            .build()
//
//        return parser
//            .parseSignedClaims(token)
//            .payload
//    }
//}