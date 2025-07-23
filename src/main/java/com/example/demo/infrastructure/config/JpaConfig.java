package com.example.demo.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA configuration for the demo module.
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.demo.infrastructure.database.repository"
)
public class JpaConfig {
}
