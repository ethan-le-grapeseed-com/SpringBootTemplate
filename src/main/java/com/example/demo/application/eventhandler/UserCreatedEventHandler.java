package com.example.demo.application.eventhandler;

import com.example.core.domain.event.DomainEventHandler;
import com.example.demo.domain.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Handler for UserCreatedEvent.
 */
@Component
@Slf4j
public class UserCreatedEventHandler implements DomainEventHandler<UserCreatedEvent> {
    
    @Override
    public void handle(UserCreatedEvent event) {
        log.info("User created: {} {} with email {}", 
            event.getFirstName(), 
            event.getLastName(), 
            event.getEmail().getValue());
        
        // Here you could add additional logic like:
        // - Sending welcome email
        // - Creating user profile
        // - Logging to analytics
        // - etc.
    }
    
    @Override
    public Class<UserCreatedEvent> getEventType() {
        return UserCreatedEvent.class;
    }
}
