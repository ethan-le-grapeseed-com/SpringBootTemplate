package com.example.core.application.command;

import com.example.core.application.usecase.UseCaseHandler;

/**
 * Interface for command handlers.
 * 
 * @param <TCommand> The command type
 * @param <TResponse> The response type
 */
public interface CommandHandler<TCommand extends Command<TResponse>, TResponse> 
    extends UseCaseHandler<TCommand, TResponse> {
}
