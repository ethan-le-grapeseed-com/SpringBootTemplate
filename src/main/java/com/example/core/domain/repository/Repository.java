package com.example.core.domain.repository;

import com.example.core.domain.model.AggregateRoot;

import java.util.List;
import java.util.Optional;

/**
 * Base repository interface for aggregate roots.
 * 
 * @param <T> The aggregate root type
 * @param <ID> The identifier type
 */
public interface Repository<T extends AggregateRoot<ID>, ID> {
    
    /**
     * Finds an aggregate by its identifier.
     * 
     * @param id The identifier
     * @return Optional containing the aggregate if found
     */
    Optional<T> findById(ID id);
    
    /**
     * Finds all aggregates.
     * 
     * @return List of all aggregates
     */
    List<T> findAll();
    
    /**
     * Saves an aggregate.
     * 
     * @param aggregate The aggregate to save
     * @return The saved aggregate
     */
    T save(T aggregate);
    
    /**
     * Deletes an aggregate by its identifier.
     * 
     * @param id The identifier of the aggregate to delete
     */
    void deleteById(ID id);
    
    /**
     * Checks if an aggregate exists with the given identifier.
     * 
     * @param id The identifier to check
     * @return true if exists, false otherwise
     */
    boolean existsById(ID id);
}
