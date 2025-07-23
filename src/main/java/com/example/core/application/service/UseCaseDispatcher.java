package com.example.core.application.service;

import com.example.core.application.usecase.UseCase;

/**
 * Service for dispatching use cases to their handlers.
 */
public interface UseCaseDispatcher {
    
    /**
     * Dispatches a use case to its appropriate handler.
     * 
     * @param useCase The use case to dispatch
     * @param <TResponse> The response type
     * @return The response from the handler
     */
    <TResponse> TResponse dispatch(UseCase<TResponse> useCase);
}
