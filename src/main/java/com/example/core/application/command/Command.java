package com.example.core.application.command;

import com.example.core.application.usecase.UseCase;

/**
 * Base interface for all commands.
 * 
 * @param <TResponse> The response type
 */
public interface Command<TResponse> extends UseCase<TResponse> {
}
