package com.example.core.common.exception;

/**
 * Exception thrown when an entity is not found.
 */
public class EntityNotFoundException extends DomainException {
    
    public EntityNotFoundException(String entityName, Object id) {
        super(String.format("Entity '%s' with id '%s' was not found", entityName, id));
    }
    
    public EntityNotFoundException(String message) {
        super(message);
    }
}
