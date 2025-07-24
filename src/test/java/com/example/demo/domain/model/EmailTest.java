package com.example.demo.domain.model;

import com.example.core.domain.exception.BusinessRuleViolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Email value object.
 */
class EmailTest {
    
    @Test
    void should_create_valid_email() {
        // Arrange
        String validEmail = "test@example.com";
        
        // Act
        Email email = Email.of(validEmail);
        
        // Assert
        assertEquals(validEmail, email.getValue());
    }
    
    @Test
    void should_normalize_email_to_lowercase() {
        // Arrange
        String mixedCaseEmail = "Test@Example.COM";
        
        // Act
        Email email = Email.of(mixedCaseEmail);
        
        // Assert
        assertEquals("test@example.com", email.getValue());
    }
    
    @Test
    void should_trim_email_whitespace() {
        // Arrange
        String emailWithWhitespace = "  test@example.com  ";
        
        // Act
        Email email = Email.of(emailWithWhitespace);
        
        // Assert
        assertEquals("test@example.com", email.getValue());
    }
    
    @Test
    void should_throw_exception_for_null_email() {
        // Act & Assert
        assertThrows(BusinessRuleViolationException.class, () -> Email.of(null));
    }
    
    @Test
    void should_throw_exception_for_empty_email() {
        // Act & Assert
        assertThrows(BusinessRuleViolationException.class, () -> Email.of(""));
    }
    
    @Test
    void should_throw_exception_for_invalid_email_format() {
        // Act & Assert
        assertThrows(BusinessRuleViolationException.class, () -> Email.of("invalid-email"));
    }
    
    @Test
    void should_be_equal_when_same_value() {
        // Arrange
        Email email1 = Email.of("test@example.com");
        Email email2 = Email.of("test@example.com");
        
        // Act & Assert
        assertEquals(email1, email2);
        assertEquals(email1.hashCode(), email2.hashCode());
    }
    
    @Test
    void should_not_be_equal_when_different_value() {
        // Arrange
        Email email1 = Email.of("test1@example.com");
        Email email2 = Email.of("test2@example.com");
        
        // Act & Assert
        assertNotEquals(email1, email2);
    }
}
