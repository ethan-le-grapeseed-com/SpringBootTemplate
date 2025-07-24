package com.example.core.application.service;

/**
 * Security service interface for application layer.
 * This provides a clean abstraction for security operations without depending on infrastructure details.
 */
public interface SecurityService {
    
    /**
     * Get current authenticated user ID.
     */
    String getCurrentUserId();
    
    /**
     * Check if current user has permission.
     */
    boolean hasPermission(String permission);
    
    /**
     * Check if current user has role.
     */
    boolean hasRole(String role);
    
    /**
     * Get current user roles.
     */
    java.util.Set<String> getCurrentUserRoles();
}
