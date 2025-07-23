package com.example.core.infrastructure.cache.adapter;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Cache adapter for centralized cache operations.
 * Provides abstraction over Spring Cache with type-safe operations.
 */
@Component
public class CacheAdapter {
    
    private final CacheManager cacheManager;
    
    public CacheAdapter(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
    /**
     * Get value from cache.
     * 
     * @param cacheName The cache name
     * @param key The cache key
     * @param type The expected value type
     * @return Optional containing the cached value if present
     */
    public <T> Optional<T> get(String cacheName, String key, Class<T> type) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Cache.ValueWrapper wrapper = cache.get(key);
            if (wrapper != null) {
                Object value = wrapper.get();
                if (type.isInstance(value)) {
                    return Optional.of(type.cast(value));
                }
            }
        }
        return Optional.empty();
    }
    
    /**
     * Put value into cache.
     * 
     * @param cacheName The cache name
     * @param key The cache key
     * @param value The value to cache
     */
    public void put(String cacheName, String key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }
    
    /**
     * Remove value from cache.
     * 
     * @param cacheName The cache name
     * @param key The cache key
     */
    public void evict(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }
    
    /**
     * Clear entire cache.
     * 
     * @param cacheName The cache name
     */
    public void clear(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }
}
