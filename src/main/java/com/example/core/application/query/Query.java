package com.example.core.application.query;

import com.example.core.application.usecase.UseCase;

/**
 * Base interface for all queries.
 * 
 * @param <TResponse> The response type
 */
public interface Query<TResponse> extends UseCase<TResponse> {
}
