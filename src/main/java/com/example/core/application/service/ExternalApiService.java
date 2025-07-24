package com.example.core.application.service;

import org.springframework.http.ResponseEntity;

/**
 * External API service interface for application layer.
 * This provides a clean abstraction for external API calls without depending on infrastructure details.
 */
public interface ExternalApiService {
    
    /**
     * Send GET request to external API.
     */
    <T> ResponseEntity<T> get(String endpoint, String path, Class<T> responseType);
    
    /**
     * Send POST request to external API.
     */
    <T> ResponseEntity<T> post(String endpoint, String path, Object request, Class<T> responseType);
    
    /**
     * Send PUT request to external API.
     */
    <T> ResponseEntity<T> put(String endpoint, String path, Object request, Class<T> responseType);
    
    /**
     * Send DELETE request to external API.
     */
    void delete(String endpoint, String path);
}
