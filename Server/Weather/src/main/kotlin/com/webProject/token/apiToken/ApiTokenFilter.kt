package com.webProject.token.apiToken

import com.webProject.token.jwtToken.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import java.io.IOException
import java.net.http.HttpHeaders
import java.util.*
import org.springframework.http.*

@Component
class ApiTokenFilter() : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("API ")) {
            val url = "http://localhost:8080/api/web/users/authCheck"
            val headers = HttpHeaders()
            headers.add("Authorization", authHeader)
            val httpEntity = HttpEntity<String>("", headers)

            RestTemplate().exchange(url, HttpMethod.GET, httpEntity, Any::class.java)
            return
        }

    }
}