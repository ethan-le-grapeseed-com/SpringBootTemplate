package com.example.demo.domain.model;

import com.example.core.common.exception.BusinessRuleViolationException;
import com.example.core.domain.model.AggregateRoot;
import com.example.demo.domain.event.UserCreatedEvent;
import com.example.demo.domain.event.UserEmailChangedEvent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * User aggregate root demonstrating DDD concepts.
 */
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA requirement
public class User extends AggregateRoot<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(name = "email", nullable = false, unique = true)
    private String emailValue;
    
    @Column(nullable = false)
    private Instant createdAt;
    
    @Column
    private Instant updatedAt;
    
    // Constructor for creating new users
    private User(String firstName, String lastName, Email email) {
        super(null); // ID will be set by JPA
        this.firstName = validateAndTrimName(firstName, "First name");
        this.lastName = validateAndTrimName(lastName, "Last name");
        this.emailValue = email.getValue();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    // Factory method for creating users
    public static User create(String firstName, String lastName, Email email) {
        User user = new User(firstName, lastName, email);
        user.addDomainEvent(new UserCreatedEvent(user.getFirstName(), user.getLastName(), email));
        return user;
    }
    
    // Getter for email as Email value object
    public Email getEmail() {
        return Email.of(emailValue);
    }
    
    // Business method for changing email
    public void changeEmail(Email newEmail) {
        if (newEmail == null) {
            throw new BusinessRuleViolationException("Email cannot be null");
        }
        
        Email currentEmail = getEmail();
        if (!currentEmail.equals(newEmail)) {
            this.emailValue = newEmail.getValue();
            this.updatedAt = Instant.now();
            
            addDomainEvent(new UserEmailChangedEvent(this.id, currentEmail, newEmail));
        }
    }
    
    // Business method for updating name
    public void updateName(String firstName, String lastName) {
        this.firstName = validateAndTrimName(firstName, "First name");
        this.lastName = validateAndTrimName(lastName, "Last name");
        this.updatedAt = Instant.now();
    }
    
    // Business rule validation
    private String validateAndTrimName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessRuleViolationException(fieldName + " cannot be null or empty");
        }
        
        String trimmedName = name.trim();
        if (trimmedName.length() < 2) {
            throw new BusinessRuleViolationException(fieldName + " must be at least 2 characters long");
        }
        
        if (trimmedName.length() > 50) {
            throw new BusinessRuleViolationException(fieldName + " cannot be longer than 50 characters");
        }
        
        return trimmedName;
    }
    
    // Computed property
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    // Override getId from AggregateRoot to return the JPA-managed ID
    @Override
    public Long getId() {
        return id;
    }
}
