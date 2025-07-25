package com.example.core.infrastructure.cache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Cache configuration for application caching.
 */
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    @Primary
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("users", "entities", "queries");
    }
}
