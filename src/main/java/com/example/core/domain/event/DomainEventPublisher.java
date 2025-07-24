package com.example.core.domain.event;

import java.util.List;

/**
 * Service for publishing domain events.
 */
public interface DomainEventPublisher {
    
    /**
     * Publishes a single domain event.
     * 
     * @param event The event to publish
     */
    void publish(DomainEvent event);
    
    /**
     * Publishes multiple domain events.
     * 
     * @param events The events to publish
     */
    void publishAll(List<DomainEvent> events);
}
