package com.example.core.infrastructure.database.adapter;

import com.example.core.domain.model.AggregateRoot;
import com.example.core.domain.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Base implementation for database access adapters using JPA.
 * Provides transactional database operations.
 * 
 * @param <T> The aggregate root type
 * @param <ID> The identifier type
 */
@Transactional
public abstract class DatabaseRepositoryAdapter<T extends AggregateRoot<ID>, ID> 
    implements Repository<T, ID> {
    
    protected abstract JpaRepository<T, ID> getJpaRepository();
    
    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        return getJpaRepository().findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return getJpaRepository().findAll();
    }
    
    @Override
    public T save(T aggregate) {
        return getJpaRepository().save(aggregate);
    }
    
    @Override
    public void deleteById(ID id) {
        getJpaRepository().deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        return getJpaRepository().existsById(id);
    }
}
