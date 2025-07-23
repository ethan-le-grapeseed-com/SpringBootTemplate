package com.example.core.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Base class for all entities in DDD.
 * 
 * @param <T> The type of the entity identifier
 */
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Entity<T> {
    
    @EqualsAndHashCode.Include
    private final T id;
    
    protected Entity(T id) {
        if (id == null) {
            throw new IllegalArgumentException("Entity id cannot be null");
        }
        this.id = id;
    }
    
    /**
     * Checks if this entity has the same identity as another entity.
     * 
     * @param other The other entity to compare with
     * @return true if they have the same identity, false otherwise
     */
    public boolean hasSameIdentityAs(Entity<T> other) {
        return other != null && this.id.equals(other.id);
    }
}
