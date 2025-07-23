package com.example.core.application.query;

import com.example.core.application.usecase.UseCaseHandler;

/**
 * Interface for query handlers.
 * 
 * @param <TQuery> The query type
 * @param <TResponse> The response type
 */
public interface QueryHandler<TQuery extends Query<TResponse>, TResponse> 
    extends UseCaseHandler<TQuery, TResponse> {
}
