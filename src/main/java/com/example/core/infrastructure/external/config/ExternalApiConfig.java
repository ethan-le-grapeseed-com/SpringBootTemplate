package com.example.core.infrastructure.external.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Configuration for external API calls with resilience patterns.
 */
@Configuration
public class ExternalApiConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofMillis(1000))
            .slidingWindowSize(2)
            .minimumNumberOfCalls(2)
            .build();
    }
    
    @Bean
    public CircuitBreaker externalApiCircuitBreaker(CircuitBreakerConfig config) {
        return CircuitBreaker.of("externalApi", config);
    }
    
    @Bean
    public RetryConfig retryConfig() {
        return RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofMillis(500))
            .build();
    }
    
    @Bean
    public Retry externalApiRetry(RetryConfig config) {
        return Retry.of("externalApi", config);
    }
}
