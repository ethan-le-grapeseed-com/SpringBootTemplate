package com.example.core.common.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a validation error.
 */
@Getter
@RequiredArgsConstructor
public class ValidationError {
    private final String propertyName;
    private final String errorMessage;
}
