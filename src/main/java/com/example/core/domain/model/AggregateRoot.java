package com.example.core.domain.model;

import com.example.core.common.event.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for aggregate roots in DDD.
 * Manages domain events and provides common functionality.
 */
public abstract class AggregateRoot<T> extends Entity<T> {
    
    private final List<DomainEvent> domainEvents = new ArrayList<>();
    
    // Protected no-arg constructor for JPA
    protected AggregateRoot() {
        super();
    }
    
    protected AggregateRoot(T id) {
        super(id);
    }
    
    /**
     * Adds a domain event to be published.
     * 
     * @param event The domain event to add
     */
    protected void addDomainEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }
    
    /**
     * Gets all unpublished domain events.
     * 
     * @return Unmodifiable list of domain events
     */
    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
    
    /**
     * Clears all domain events after they have been published.
     */
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }
    
    /**
     * Checks if there are any unpublished domain events.
     * 
     * @return true if there are unpublished events, false otherwise
     */
    public boolean hasDomainEvents() {
        return !domainEvents.isEmpty();
    }
}
