package com.example.core.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * Generic API response wrapper for consistent REST API responses.
 * This is a shared component that can be reused across all Spring Boot projects.
 */
@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    private final boolean success;
    private final T data;
    private final String message;
    private final String error;
    private final Instant timestamp = Instant.now();
    
    /**
     * Create a successful response with data and message.
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message, null);
    }
    
    /**
     * Create a successful response with data only.
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, null);
    }
    
    /**
     * Create a successful response with message only (no data).
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, null, message, null);
    }
    
    /**
     * Create an error response with error message.
     */
    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(false, null, null, error);
    }
    
    /**
     * Create an error response with custom message and error.
     */
    public static <T> ApiResponse<T> error(String message, String error) {
        return new ApiResponse<>(false, null, message, error);
    }
}
