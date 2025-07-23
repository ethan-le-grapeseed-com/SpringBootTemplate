package com.example.core.infrastructure.external.adapter;

import com.example.core.infrastructure.external.exception.ExternalApiException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

/**
 * Adapter for external API calls with resilience patterns.
 * Provides circuit breaker, retry, and transient fault handling.
 */
@Component
public class ExternalApiAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(ExternalApiAdapter.class);
    
    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;
    
    public ExternalApiAdapter(RestTemplate restTemplate, 
                             CircuitBreaker externalApiCircuitBreaker,
                             Retry externalApiRetry) {
        this.restTemplate = restTemplate;
        this.circuitBreaker = externalApiCircuitBreaker;
        this.retry = externalApiRetry;
    }
    
    /**
     * Execute GET request with resilience patterns.
     * 
     * @param serviceName The name of the external service
     * @param url The request URL
     * @param responseType The expected response type
     * @return The response entity
     */
    public <T> ResponseEntity<T> get(String serviceName, String url, Class<T> responseType) {
        return executeWithResilience(serviceName, () -> {
            try {
                logger.debug("Making GET request to {} for service {}", url, serviceName);
                return restTemplate.getForEntity(url, responseType);
            } catch (RestClientException e) {
                logger.error("GET request failed for service {} at URL {}: {}", serviceName, url, e.getMessage());
                throw new ExternalApiException(serviceName, "GET request failed: " + e.getMessage(), e);
            }
        });
    }
    
    /**
     * Execute POST request with resilience patterns.
     * 
     * @param serviceName The name of the external service
     * @param url The request URL
     * @param request The request body
     * @param responseType The expected response type
     * @return The response entity
     */
    public <T> ResponseEntity<T> post(String serviceName, String url, Object request, Class<T> responseType) {
        return executeWithResilience(serviceName, () -> {
            try {
                logger.debug("Making POST request to {} for service {}", url, serviceName);
                return restTemplate.postForEntity(url, request, responseType);
            } catch (RestClientException e) {
                logger.error("POST request failed for service {} at URL {}: {}", serviceName, url, e.getMessage());
                throw new ExternalApiException(serviceName, "POST request failed: " + e.getMessage(), e);
            }
        });
    }
    
    /**
     * Execute generic HTTP request with resilience patterns.
     * 
     * @param serviceName The name of the external service
     * @param url The request URL
     * @param method The HTTP method
     * @param requestEntity The request entity
     * @param responseType The expected response type
     * @return The response entity
     */
    public <T> ResponseEntity<T> exchange(String serviceName, String url, HttpMethod method, 
                                         HttpEntity<?> requestEntity, Class<T> responseType) {
        return executeWithResilience(serviceName, () -> {
            try {
                logger.debug("Making {} request to {} for service {}", method, url, serviceName);
                return restTemplate.exchange(url, method, requestEntity, responseType);
            } catch (RestClientException e) {
                logger.error("{} request failed for service {} at URL {}: {}", method, serviceName, url, e.getMessage());
                throw new ExternalApiException(serviceName, method + " request failed: " + e.getMessage(), e);
            }
        });
    }
    
    /**
     * Execute operation with circuit breaker and retry patterns.
     */
    private <T> T executeWithResilience(String serviceName, Supplier<T> operation) {
        Supplier<T> decoratedOperation = CircuitBreaker
            .decorateSupplier(circuitBreaker, operation);
        
        decoratedOperation = Retry
            .decorateSupplier(retry, decoratedOperation);
        
        try {
            return decoratedOperation.get();
        } catch (Exception e) {
            logger.error("All resilience attempts failed for service {}: {}", serviceName, e.getMessage());
            if (e instanceof ExternalApiException) {
                throw e;
            }
            throw new ExternalApiException(serviceName, "Service call failed after all retries", e);
        }
    }
}
