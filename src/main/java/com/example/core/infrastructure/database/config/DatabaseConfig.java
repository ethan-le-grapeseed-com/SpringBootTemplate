package com.example.core.infrastructure.database.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database configuration for JPA repositories and entities.
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.*.infrastructure.database.repository"
)
@EntityScan(
    basePackages = "com.example.*.domain.model"
)
@EnableTransactionManagement
public class DatabaseConfig {
}
