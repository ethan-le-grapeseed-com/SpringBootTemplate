package com.example.demo.domain.repository;

import com.example.core.domain.repository.Repository;
import com.example.demo.domain.model.Email;
import com.example.demo.domain.model.User;

import java.util.Optional;

/**
 * Repository interface for User aggregate.
 */
public interface UserRepository extends Repository<User, Long> {
    
    /**
     * Finds a user by email address.
     * 
     * @param email The email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(Email email);
    
    /**
     * Checks if a user exists with the given email.
     * 
     * @param email The email to check
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(Email email);
}
