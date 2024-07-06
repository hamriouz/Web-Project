package com.webProject.config

import com.webProject.user.UserDetailsLoader
import com.webProject.user.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.Duration


@Configuration
@EnableCaching
class Config(
    @Value("\${web.country.getCountry.cache.name}")
    private val getCountryCacheName: String,
    @Value("\${web.country.getCountryWeather.cache.name}")
    private val getCountryWeatherCacheName: String,
    @Value("\${web.country.cache.TTL.minutes}")
    private val cacheTTLMinutes: Long,
    private val userRepository: UserRepository,
    private val userDetailLoader: UserDetailsLoader
) {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailLoader)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun redisCacheManager(connectionFactory: RedisConnectionFactory): RedisCacheManager {
        val builder = RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
        return builder
            .withCacheConfiguration(
                getCountryCacheName,
                RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(cacheTTLMinutes))
                    .disableCachingNullValues()
            )
            .withCacheConfiguration(
                getCountryWeatherCacheName,
                RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(cacheTTLMinutes))
                    .disableCachingNullValues()
            )
            .build()
    }

    @Bean
    @Primary
    fun simpleCacheManager(): CacheManager? {
        val cacheManager = SimpleCacheManager()
        val caches: MutableList<Cache> = ArrayList()
        caches.add(ConcurrentMapCache("getCountries"))
        cacheManager.setCaches(caches)
        return cacheManager
    }

}