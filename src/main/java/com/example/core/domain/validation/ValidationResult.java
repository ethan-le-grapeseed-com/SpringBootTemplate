package com.example.core.domain.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * Represents the result of a validation operation.
 */
@Getter
@RequiredArgsConstructor
public class ValidationResult {
    private final boolean isValid;
    private final List<ValidationError> errors;
    
    public static ValidationResult success() {
        return new ValidationResult(true, Collections.emptyList());
    }
    
    public static ValidationResult failure(List<ValidationError> errors) {
        return new ValidationResult(false, errors);
    }
    
    public String getErrorMessage() {
        return errors.stream()
            .map(ValidationError::getErrorMessage)
            .reduce((a, b) -> a + ", " + b)
            .orElse("");
    }
}
