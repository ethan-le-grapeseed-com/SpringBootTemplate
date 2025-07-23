package com.example.demo.domain.model;

import com.example.core.common.exception.BusinessRuleViolationException;
import com.example.core.domain.model.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing an email address.
 * Demonstrates value object implementation.
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class Email extends ValueObject {
    
    private final String value;
    
    private Email(String value) {
        this.value = value;
    }
    
    public static Email of(String email) {
        validateEmail(email);
        return new Email(email.toLowerCase().trim());
    }
    
    private static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new BusinessRuleViolationException("Email cannot be null or empty");
        }
        
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new BusinessRuleViolationException("Invalid email format: " + email);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Email email = (Email) obj;
        return value.equals(email.value);
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    @Override
    public String toString() {
        return value;
    }
}
