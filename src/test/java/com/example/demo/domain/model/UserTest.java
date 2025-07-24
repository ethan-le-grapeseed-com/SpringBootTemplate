package com.example.demo.domain.model;

import com.example.core.domain.exception.BusinessRuleViolationException;
import com.example.demo.domain.event.UserCreatedEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for User aggregate root.
 */
class UserTest {
    
    @Test
    void should_create_user_with_valid_data() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        Email email = Email.of("john.doe@example.com");
        
        // Act
        User user = User.create(firstName, lastName, email);
        
        // Assert
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals("John Doe", user.getFullName());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        assertTrue(user.hasDomainEvents());
        assertEquals(1, user.getDomainEvents().size());
        assertTrue(user.getDomainEvents().get(0) instanceof UserCreatedEvent);
    }
    
    @Test
    void should_throw_exception_for_null_first_name() {
        // Arrange
        Email email = Email.of("test@example.com");
        
        // Act & Assert
        assertThrows(BusinessRuleViolationException.class, 
            () -> User.create(null, "Doe", email));
    }
    
    @Test
    void should_throw_exception_for_empty_first_name() {
        // Arrange
        Email email = Email.of("test@example.com");
        
        // Act & Assert
        assertThrows(BusinessRuleViolationException.class, 
            () -> User.create("", "Doe", email));
    }
    
    @Test
    void should_throw_exception_for_short_first_name() {
        // Arrange
        Email email = Email.of("test@example.com");
        
        // Act & Assert
        assertThrows(BusinessRuleViolationException.class, 
            () -> User.create("A", "Doe", email));
    }
    
    @Test
    void should_throw_exception_for_long_first_name() {
        // Arrange
        Email email = Email.of("test@example.com");
        String longName = "A".repeat(51);
        
        // Act & Assert
        assertThrows(BusinessRuleViolationException.class, 
            () -> User.create(longName, "Doe", email));
    }
    
    @Test
    void should_change_email_successfully() {
        // Arrange
        User user = User.create("John", "Doe", Email.of("john@example.com"));
        Email newEmail = Email.of("john.doe@example.com");
        user.clearDomainEvents(); // Clear creation event
        
        // Act
        user.changeEmail(newEmail);
        
        // Assert
        assertEquals(newEmail, user.getEmail());
        assertTrue(user.hasDomainEvents());
        assertEquals(1, user.getDomainEvents().size());
    }
    
    @Test
    void should_not_create_event_when_email_unchanged() {
        // Arrange
        Email email = Email.of("john@example.com");
        User user = User.create("John", "Doe", email);
        user.clearDomainEvents(); // Clear creation event
        
        // Act
        user.changeEmail(email);
        
        // Assert
        assertEquals(email, user.getEmail());
        assertFalse(user.hasDomainEvents());
    }
    
    @Test
    void should_update_name_successfully() {
        // Arrange
        User user = User.create("John", "Doe", Email.of("john@example.com"));
        
        // Act
        user.updateName("Jane", "Smith");
        
        // Assert
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("Jane Smith", user.getFullName());
    }
    
    @Test
    void should_trim_names_when_creating_user() {
        // Arrange
        Email email = Email.of("test@example.com");
        
        // Act
        User user = User.create("  John  ", "  Doe  ", email);
        
        // Assert
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }
}
