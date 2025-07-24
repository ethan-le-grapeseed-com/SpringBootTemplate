package com.example.core.domain.event;

/**
 * Interface for handling domain events.
 * 
 * @param <T> The type of domain event to handle
 */
public interface DomainEventHandler<T extends DomainEvent> {
    
    /**
     * Handles the domain event.
     * 
     * @param event The domain event to handle
     */
    void handle(T event);
    
    /**
     * Gets the type of event this handler can process.
     */
    Class<T> getEventType();
}
