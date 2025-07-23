package com.example.core.infrastructure.messaging.adapter;

import com.example.core.common.event.DomainEvent;
import com.example.core.common.event.DomainEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Messaging adapter for publishing and consuming messages.
 * Currently uses Spring's ApplicationEventPublisher for internal events.
 * Can be extended to support external message brokers.
 */
@Component
public class MessagingAdapter implements DomainEventPublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(MessagingAdapter.class);
    
    private final ApplicationEventPublisher applicationEventPublisher;
    
    public MessagingAdapter(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    
    @Override
    public void publishEvent(DomainEvent event) {
        logger.debug("Publishing domain event: {}", event.getClass().getSimpleName());
        applicationEventPublisher.publishEvent(event);
    }
    
    /**
     * Publish message to external message broker.
     * This is a placeholder for future implementation.
     * 
     * @param topic The message topic/queue
     * @param message The message to publish
     */
    public void publishMessage(String topic, Object message) {
        logger.info("Publishing message to topic {}: {}", topic, message.getClass().getSimpleName());
        // Future: Implement external message broker publishing
        // For example: RabbitMQ, Apache Kafka, Azure Service Bus, etc.
    }
    
    /**
     * Send command message to external service.
     * This is a placeholder for future implementation.
     * 
     * @param destination The destination service/queue
     * @param command The command to send
     */
    public void sendCommand(String destination, Object command) {
        logger.info("Sending command to {}: {}", destination, command.getClass().getSimpleName());
        // Future: Implement command messaging
    }
}
