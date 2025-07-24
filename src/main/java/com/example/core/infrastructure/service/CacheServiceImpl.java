package com.example.core.infrastructure.service;

import com.example.core.application.service.CacheService;
import com.example.core.infrastructure.cache.adapter.CacheAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of CacheService using CacheAdapter.
 * This bridges the application layer interface with infrastructure implementation.
 */
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
    
    private final CacheAdapter cacheAdapter;
    
    @Override
    public <T> Optional<T> get(String region, String key, Class<T> type) {
        return cacheAdapter.get(region, key, type);
    }
    
    @Override
    public void put(String region, String key, Object value) {
        cacheAdapter.put(region, key, value);
    }
    
    @Override
    public void remove(String region, String key) {
        // remove method not yet implemented in CacheAdapter
        throw new UnsupportedOperationException("remove method not yet implemented");
    }
    
    @Override
    public void clearRegion(String region) {
        // clearRegion method not yet implemented in CacheAdapter
        throw new UnsupportedOperationException("clearRegion method not yet implemented");
    }
}
