package com.example.core.infrastructure.config;

import com.example.core.application.usecase.UseCaseHandler;
import com.example.core.infrastructure.service.SpringUseCaseDispatcher;
import com.example.core.infrastructure.database.config.DatabaseConfig;
import com.example.core.infrastructure.cache.config.CacheConfig;
import com.example.core.infrastructure.external.config.ExternalApiConfig;
import com.example.core.infrastructure.messaging.config.MessagingConfig;
import com.example.core.infrastructure.security.config.SecurityConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Main infrastructure configuration that imports all infrastructure layer configurations.
 * This acts as the central configuration hub for the infrastructure layer.
 */
@Configuration
@Import({
    DatabaseConfig.class,
    CacheConfig.class,
    ExternalApiConfig.class,
    MessagingConfig.class,
    SecurityConfig.class,
    WebConfig.class,
    LoggingConfig.class
})
public class UseCaseConfig {
    
    @Bean
    public Map<Class<?>, UseCaseHandler<?, ?>> useCaseHandlers(ApplicationContext applicationContext) {
        return applicationContext.getBeansOfType(UseCaseHandler.class)
            .values()
            .stream()
            .collect(Collectors.toMap(
                handler -> getUseCaseClass(handler),
                handler -> handler
            ));
    }
    
    @SuppressWarnings("unchecked")
    private Class<?> getUseCaseClass(UseCaseHandler<?, ?> handler) {
        // Get the actual class (not the CGLIB proxy)
        Class<?> handlerClass = handler.getClass();
        String handlerClassName = handlerClass.getSimpleName();
        
        // Handle CGLIB proxy
        if (handlerClassName.contains("$$")) {
            handlerClass = handlerClass.getSuperclass();
            handlerClassName = handlerClass.getSimpleName();
        }
        
        String useCaseClassName = handlerClassName.replace("Handler", "");
        
        try {
            String packageName = handlerClass.getPackage().getName();
            // Try to find the use case in the same package first
            return Class.forName(packageName + "." + useCaseClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find use case class for handler: " + handlerClassName, e);
        }
    }
}
