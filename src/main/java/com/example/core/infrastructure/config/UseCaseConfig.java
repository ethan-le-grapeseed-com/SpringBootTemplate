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
    SecurityConfig.class
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
        // This is a simplified approach - in a real application you might want to use reflection
        // or annotations to determine the use case type
        String handlerClassName = handler.getClass().getSimpleName();
        String useCaseClassName = handlerClassName.replace("Handler", "");
        
        try {
            String packageName = handler.getClass().getPackage().getName();
            String useCasePackage = packageName.replace(".command", "").replace(".query", "");
            return Class.forName(useCasePackage + "." + useCaseClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find use case class for handler: " + handlerClassName, e);
        }
    }
}
