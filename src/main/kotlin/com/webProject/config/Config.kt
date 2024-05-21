package com.webProject.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import java.time.Duration

@Configuration
class Config(
    @Value("\${web.country.getCountry.cache.name}")
    private val getCountryCacheName: String,
    @Value("\${web.country.getCountryWeather.cache.name}")
    private val getCountryWeatherCacheName: String,
    @Value("\${web.country.cache.TTL.minutes}")
    private val cacheTTLMinutes: Long,
) {

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