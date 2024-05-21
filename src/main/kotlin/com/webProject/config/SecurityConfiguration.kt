package com.webProject.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(
        http: HttpSecurity
    ): SecurityFilterChain {
        http
            .authorizeHttpRequests { requests ->
                requests // Swagger
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll() // Auth
                    .requestMatchers("/api/auth/**").permitAll() // Other endpoints
                    .requestMatchers("/api/**").permitAll()
            }
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

//    @Bean
//    @Throws(Exception::class)
//    fun authenticationManager(
//        http: HttpSecurity,
//        bCryptPasswordEncoder: BCryptPasswordEncoder?,
//        sessionService: SessionService<Session?>
//    ): AuthenticationManager {
//        val builder = http.getSharedObject(
//            AuthenticationManagerBuilder::class.java
//        )
//        builder
//            .userDetailsService<UserDetailsService>(sessionService)
//            .passwordEncoder(bCryptPasswordEncoder)
//        return builder.build()
//    }
}