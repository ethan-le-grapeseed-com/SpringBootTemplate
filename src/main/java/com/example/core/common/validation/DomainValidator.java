package com.example.core.common.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Domain validator for business rule validation.
 */
@Component
@RequiredArgsConstructor
public class DomainValidator {
    
    private final Validator validator;
    
    /**
     * Validates an object and throws exception if invalid.
     */
    public <T> void validateAndThrow(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Validation failed: " + message);
        }
    }
    
    /**
     * Validates an object and returns validation result.
     */
    public <T> ValidationResult validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (violations.isEmpty()) {
            return ValidationResult.success();
        }
        
        var errors = violations.stream()
            .map(v -> new ValidationError(v.getPropertyPath().toString(), v.getMessage()))
            .collect(Collectors.toList());
            
        return ValidationResult.failure(errors);
    }
}
