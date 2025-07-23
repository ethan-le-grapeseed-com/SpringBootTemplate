package com.example.core.common.event;

import java.time.Instant;

/**
 * Base interface for all domain events.
 */
public interface DomainEvent {
    
    /**
     * Gets the unique identifier of the event.
     */
    String getEventId();
    
    /**
     * Gets the timestamp when the event occurred.
     */
    Instant getOccurredOn();
    
    /**
     * Gets the type of the event.
     */
    default String getEventType() {
        return this.getClass().getSimpleName();
    }
}
