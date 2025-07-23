package com.example.core.infrastructure.external.exception;

/**
 * Exception thrown when external API calls fail.
 */
public class ExternalApiException extends RuntimeException {
    
    private final String serviceName;
    private final int statusCode;
    
    public ExternalApiException(String serviceName, String message) {
        super(message);
        this.serviceName = serviceName;
        this.statusCode = 0;
    }
    
    public ExternalApiException(String serviceName, int statusCode, String message) {
        super(message);
        this.serviceName = serviceName;
        this.statusCode = statusCode;
    }
    
    public ExternalApiException(String serviceName, String message, Throwable cause) {
        super(message, cause);
        this.serviceName = serviceName;
        this.statusCode = 0;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
}
