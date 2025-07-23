package com.example.core.infrastructure.service;

import com.example.core.application.service.UseCaseDispatcher;
import com.example.core.application.usecase.UseCase;
import com.example.core.application.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Spring-based implementation of use case dispatcher.
 */
@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class SpringUseCaseDispatcher implements UseCaseDispatcher {
    
    private final Map<Class<?>, UseCaseHandler<?, ?>> handlers;
    
    @Override
    public <TResponse> TResponse dispatch(UseCase<TResponse> useCase) {
        Class<?> useCaseClass = useCase.getClass();
        UseCaseHandler<UseCase<TResponse>, TResponse> handler = 
            (UseCaseHandler<UseCase<TResponse>, TResponse>) handlers.get(useCaseClass);
            
        if (handler == null) {
            throw new IllegalArgumentException(
                "No handler found for use case: " + useCaseClass.getSimpleName());
        }
        
        return handler.handle(useCase);
    }
}
