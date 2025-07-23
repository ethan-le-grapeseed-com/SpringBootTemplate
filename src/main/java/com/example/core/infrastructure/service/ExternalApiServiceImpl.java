package com.example.core.infrastructure.service;

import com.example.core.application.service.ExternalApiService;
import com.example.core.infrastructure.external.adapter.ExternalApiAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation of ExternalApiService using ExternalApiAdapter.
 * This bridges the application layer interface with infrastructure implementation.
 */
@Service
@RequiredArgsConstructor
public class ExternalApiServiceImpl implements ExternalApiService {
    
    private final ExternalApiAdapter externalApiAdapter;
    
    @Override
    public <T> ResponseEntity<T> get(String endpoint, String path, Class<T> responseType) {
        return externalApiAdapter.get(endpoint, path, responseType);
    }
    
    @Override
    public <T> ResponseEntity<T> post(String endpoint, String path, Object request, Class<T> responseType) {
        return externalApiAdapter.post(endpoint, path, request, responseType);
    }
    
    @Override
    public <T> ResponseEntity<T> put(String endpoint, String path, Object request, Class<T> responseType) {
        // PUT method not yet implemented in ExternalApiAdapter
        throw new UnsupportedOperationException("PUT method not yet implemented");
    }
    
    @Override
    public void delete(String endpoint, String path) {
        // DELETE method not yet implemented in ExternalApiAdapter
        throw new UnsupportedOperationException("DELETE method not yet implemented");
    }
}
