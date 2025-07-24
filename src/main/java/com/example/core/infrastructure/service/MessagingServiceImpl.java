package com.example.core.infrastructure.service;

import com.example.core.application.service.MessagingService;
import com.example.core.infrastructure.messaging.adapter.MessagingAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of MessagingService using MessagingAdapter.
 * This bridges the application layer interface with infrastructure implementation.
 */
@Service
@RequiredArgsConstructor
public class MessagingServiceImpl implements MessagingService {
    
    private final MessagingAdapter messagingAdapter;
    
    @Override
    public void publishMessage(String topic, Object message) {
        messagingAdapter.publishMessage(topic, message);
    }
    
    @Override
    public void sendMessage(String destination, Object message) {
        // sendMessage method not yet implemented in MessagingAdapter
        throw new UnsupportedOperationException("sendMessage method not yet implemented");
    }
}
