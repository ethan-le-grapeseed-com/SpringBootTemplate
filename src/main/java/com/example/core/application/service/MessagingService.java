package com.example.core.application.service;

/**
 * Messaging service interface for application layer.
 * This provides a clean abstraction for messaging operations without depending on infrastructure details.
 */
public interface MessagingService {
    
    /**
     * Publish a message to a topic/queue.
     */
    void publishMessage(String topic, Object message);
    
    /**
     * Send a direct message.
     */
    void sendMessage(String destination, Object message);
}
