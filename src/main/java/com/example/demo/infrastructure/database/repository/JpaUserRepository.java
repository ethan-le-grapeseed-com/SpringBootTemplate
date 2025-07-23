package com.example.demo.infrastructure.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.domain.model.User;

import java.util.Optional;

/**
 * JPA repository interface for User entity.
 */
@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.email.value = :emailValue")
    Optional<User> findByEmailValue(@Param("emailValue") String emailValue);
}
