package com.example.core.domain.model;

/**
 * Base class for value objects in DDD.
 * Value objects are immutable and equality is based on their properties.
 */
public abstract class ValueObject {
    
    /**
     * Value objects are equal if all their properties are equal.
     * Subclasses should override equals() and hashCode() appropriately.
     */
    @Override
    public abstract boolean equals(Object obj);
    
    @Override
    public abstract int hashCode();
    
    /**
     * Checks if this value object has the same value as another.
     * 
     * @param other The other value object to compare with
     * @return true if they have the same value, false otherwise
     */
    public boolean hasSameValueAs(ValueObject other) {
        return this.equals(other);
    }
}
