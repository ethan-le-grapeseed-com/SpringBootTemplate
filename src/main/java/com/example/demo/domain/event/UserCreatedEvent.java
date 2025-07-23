package com.example.demo.domain.event;

import com.example.core.common.event.DomainEvent;
import com.example.demo.domain.model.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * Domain event fired when a user is created.
 */
@Getter
@RequiredArgsConstructor
public class UserCreatedEvent implements DomainEvent {
    
    private final String eventId = UUID.randomUUID().toString();
    private final Instant occurredOn = Instant.now();
    private final String firstName;
    private final String lastName;
    private final Email email;
}
