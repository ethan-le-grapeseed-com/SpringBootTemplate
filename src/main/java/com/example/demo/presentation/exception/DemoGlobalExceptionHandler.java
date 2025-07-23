package com.example.demo.presentation.exception;

import com.example.core.common.exception.handler.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Demo-specific global exception handler that extends the core exception handler.
 * Add any demo-specific exception handling here.
 */
@RestControllerAdvice
public class DemoGlobalExceptionHandler extends GlobalExceptionHandler {
    
    // Add demo-specific exception handlers here if needed
    // All common exception handling is inherited from core GlobalExceptionHandler
}
