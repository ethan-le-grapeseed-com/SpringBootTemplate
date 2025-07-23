package com.example.demo.infrastructure.database.adapter;

import com.example.core.infrastructure.database.adapter.DatabaseRepositoryAdapter;
import com.example.demo.domain.model.Email;
import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.infrastructure.database.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Database adapter implementation of UserRepository using JPA.
 * Provides transactional database operations for User entities.
 */
@Repository
@RequiredArgsConstructor
public class UserDatabaseAdapter extends DatabaseRepositoryAdapter<User, Long> 
    implements UserRepository {
    
    private final JpaUserRepository jpaUserRepository;
    
    @Override
    protected JpaRepository<User, Long> getJpaRepository() {
        return jpaUserRepository;
    }
    
    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaUserRepository.findByEmailValue(email.getValue());
    }
}
