package com.example.core.infrastructure.messaging.publisher;

import com.example.core.common.event.DomainEvent;
import com.example.core.common.event.DomainEventHandler;
import com.example.core.common.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Spring-based implementation of domain event publisher integrated with messaging layer.
 * Handles both internal domain events and external messaging.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unchecked")
public class SpringDomainEventPublisher implements DomainEventPublisher {
    
    private final List<DomainEventHandler<? extends DomainEvent>> eventHandlers;
    private final Map<Class<? extends DomainEvent>, List<DomainEventHandler<? extends DomainEvent>>> handlerMap;
    
    public SpringDomainEventPublisher(List<DomainEventHandler<? extends DomainEvent>> eventHandlers) {
        this.eventHandlers = eventHandlers;
        this.handlerMap = eventHandlers.stream()
            .collect(Collectors.groupingBy(DomainEventHandler::getEventType));
    }
    
    @Override
    public void publishEvent(DomainEvent event) {
        log.debug("Publishing domain event: {}", event.getClass().getSimpleName());
        
        List<DomainEventHandler<? extends DomainEvent>> handlers = handlerMap.get(event.getClass());
        
        if (handlers == null || handlers.isEmpty()) {
            log.warn("No handlers found for event type: {}", event.getClass().getName());
            return;
        }
        
        for (DomainEventHandler handler : handlers) {
            try {
                log.debug("Handling event {} with handler {}", 
                    event.getClass().getSimpleName(), 
                    handler.getClass().getSimpleName());
                handler.handle(event);
            } catch (Exception e) {
                log.error("Error handling event {} with handler {}: {}", 
                    event.getClass().getSimpleName(), 
                    handler.getClass().getSimpleName(), 
                    e.getMessage(), e);
                
                // Continue processing other handlers even if one fails
            }
        }
        
        log.debug("Finished publishing domain event: {}", event.getClass().getSimpleName());
    }
}
