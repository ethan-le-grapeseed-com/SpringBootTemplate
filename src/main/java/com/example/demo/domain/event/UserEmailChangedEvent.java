package com.example.demo.domain.event;

import com.example.core.domain.event.DomainEvent;
import com.example.demo.domain.model.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * Domain event fired when a user's email is changed.
 */
@Getter
@RequiredArgsConstructor
public class UserEmailChangedEvent implements DomainEvent {
    
    private final String eventId = UUID.randomUUID().toString();
    private final Instant occurredOn = Instant.now();
    private final Long userId;
    private final Email oldEmail;
    private final Email newEmail;
}
