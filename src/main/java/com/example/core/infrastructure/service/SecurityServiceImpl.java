package com.example.core.infrastructure.service;

import com.example.core.application.service.SecurityService;
import com.example.core.infrastructure.security.adapter.SecurityAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Implementation of SecurityService using SecurityAdapter.
 * This bridges the application layer interface with infrastructure implementation.
 */
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    
    private final SecurityAdapter securityAdapter;
    
    @Override
    public String getCurrentUserId() {
        return securityAdapter.getCurrentUserId();
    }
    
    @Override
    public boolean hasPermission(String permission) {
        return securityAdapter.hasPermission(permission);
    }
    
    @Override
    public boolean hasRole(String role) {
        // hasRole method not yet implemented in SecurityAdapter
        throw new UnsupportedOperationException("hasRole method not yet implemented");
    }
    
    @Override
    public Set<String> getCurrentUserRoles() {
        // getCurrentUserRoles method not yet implemented in SecurityAdapter
        throw new UnsupportedOperationException("getCurrentUserRoles method not yet implemented");
    }
}
