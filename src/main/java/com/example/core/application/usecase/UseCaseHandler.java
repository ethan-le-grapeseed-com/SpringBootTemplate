package com.example.core.application.usecase;

/**
 * Interface for handling use cases (commands and queries).
 * 
 * @param <TUseCase> The use case type
 * @param <TResponse> The response type
 */
public interface UseCaseHandler<TUseCase extends UseCase<TResponse>, TResponse> {
    
    /**
     * Handles the use case.
     * 
     * @param useCase The use case to handle
     * @return The response
     */
    TResponse handle(TUseCase useCase);
}
