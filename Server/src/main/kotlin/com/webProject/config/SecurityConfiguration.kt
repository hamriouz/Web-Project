package com.webProject.config

//import JwtRequestFilter

import com.webProject.token.apiToken.ApiTokenFilter
import com.webProject.token.jwtToken.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService,
    @Value("\${web.user.jwt.secret.key}") private val jwtKey: String,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val apiTokenFilter: ApiTokenFilter,

    ) {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(
        http: HttpSecurity
    ): SecurityFilterChain {
        http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/api/web/users/**").permitAll()
                    .requestMatchers("/api/**").authenticated()
            }
            .csrf { it.disable() }
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(apiTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:3000", "http://localhost:3001/", "http://localhost:3002")
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}