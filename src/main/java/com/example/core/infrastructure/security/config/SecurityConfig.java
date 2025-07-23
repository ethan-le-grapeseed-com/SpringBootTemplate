package com.example.core.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for infrastructure layer.
 * Handles authentication, authorization, and security policies.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for API testing
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Allow H2 console to work in frames
            )
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/**").permitAll() // Allow API access
                .requestMatchers("/actuator/**").permitAll() // Allow actuator access
                .requestMatchers("/h2-console/**").permitAll() // Allow H2 console access
                .requestMatchers("/swagger-ui/**").permitAll() // Allow Swagger UI access
                .requestMatchers("/swagger-ui.html").permitAll() // Allow Swagger UI access
                .requestMatchers("/v3/api-docs/**").permitAll() // Allow OpenAPI docs access
                .requestMatchers("/v3/api-docs").permitAll() // Allow OpenAPI docs access
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}
