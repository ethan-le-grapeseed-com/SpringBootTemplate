package com.example.core.application.service;

import java.util.Optional;

/**
 * Cache service interface for application layer.
 * This provides a clean abstraction for caching operations without depending on infrastructure details.
 */
public interface CacheService {
    
    /**
     * Get value from cache.
     */
    <T> Optional<T> get(String region, String key, Class<T> type);
    
    /**
     * Put value into cache.
     */
    void put(String region, String key, Object value);
    
    /**
     * Remove value from cache.
     */
    void remove(String region, String key);
    
    /**
     * Clear entire cache region.
     */
    void clearRegion(String region);
}
