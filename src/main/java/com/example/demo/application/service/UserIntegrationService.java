package com.example.demo.application.service;

import com.example.core.application.service.CacheService;
import com.example.core.application.service.ExternalApiService;
import com.example.core.application.service.MessagingService;
import com.example.core.application.service.SecurityService;
import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Example service demonstrating usage of all infrastructure layers.
 * This service shows how to integrate database, cache, external API, messaging, and security layers.
 */
@Service
@RequiredArgsConstructor
public class UserIntegrationService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserIntegrationService.class);
    
    private final UserRepository userRepository;
    private final CacheService cacheService;
    private final ExternalApiService externalApiService;
    private final MessagingService messagingService;
    private final SecurityService securityService;
    
    /**
     * Get user with integrated caching, external validation, and security.
     * 
     * @param userId The user ID
     * @return User if found and authorized
     */
    public Optional<User> getUserWithIntegration(Long userId) {
        // 1. Security check
        String currentUserId = securityService.getCurrentUserId();
        if (!securityService.hasPermission("READ_USER")) {
            logger.warn("User {} does not have permission to read user data", currentUserId);
            return Optional.empty();
        }
        
        // 2. Try cache first
        String cacheKey = "user:" + userId;
        Optional<User> cachedUser = cacheService.get("users", cacheKey, User.class);
        if (cachedUser.isPresent()) {
            logger.debug("User found in cache: {}", userId);
            return cachedUser;
        }
        
        // 3. Database lookup
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            logger.debug("User {} not found in database", userId);
            return Optional.empty();
        }
        
        // 4. Cache the result
        cacheService.put("users", cacheKey, user.get());
        
        // 5. External API validation (example)
        try {
            validateUserExternally(user.get());
        } catch (Exception e) {
            logger.warn("External validation failed for user {}: {}", userId, e.getMessage());
            // Continue anyway - this is just an example
        }
        
        // 6. Publish access event
        messagingService.publishMessage("user.events", 
            String.format("User %d accessed by %s", userId, currentUserId));
        
        return user;
    }
    
    /**
     * Example external API validation.
     */
    private void validateUserExternally(User user) {
        try {
            String validationUrl = "https://api.example.com/validate/user/" + user.getId();
            ResponseEntity<String> response = externalApiService.get(
                "user-validation-service", 
                validationUrl, 
                String.class
            );
            
            logger.debug("External validation response: {}", response.getStatusCode());
        } catch (Exception e) {
            logger.error("External validation failed: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Update user with full integration pipeline.
     * 
     * @param user The user to update
     * @return Updated user
     */
    public User updateUserWithIntegration(User user) {
        // 1. Security check
        if (!securityService.hasPermission("WRITE_USER")) {
            throw new SecurityException("Insufficient permissions to update user");
        }
        
        // 2. Save to database
        User savedUser = userRepository.save(user);
        
        // 3. Update cache
        String cacheKey = "user:" + user.getId();
        cacheService.put("users", cacheKey, savedUser);
        
        // 4. Notify external systems
        try {
            notifyExternalSystemOfUpdate(savedUser);
        } catch (Exception e) {
            logger.warn("Failed to notify external systems: {}", e.getMessage());
            // Continue anyway - this is not critical
        }
        
        // 5. Publish update event
        messagingService.publishMessage("user.events", 
            String.format("User %d updated", savedUser.getId()));
        
        return savedUser;
    }
    
    /**
     * Example external system notification.
     */
    private void notifyExternalSystemOfUpdate(User user) {
        String notificationUrl = "https://api.example.com/notify/user-updated";
        
        // Create notification payload
        var notification = new Object() {
            public final Long userId = user.getId();
            public final String email = user.getEmail().getValue();
            public final String timestamp = java.time.Instant.now().toString();
        };
        
        ResponseEntity<String> response = externalApiService.post(
            "notification-service", 
            notificationUrl, 
            notification, 
            String.class
        );
        
        logger.debug("Notification sent, response: {}", response.getStatusCode());
    }
}
