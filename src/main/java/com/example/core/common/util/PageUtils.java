package com.example.core.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Utility class for pagination and sorting operations.
 * Provides common methods for creating Pageable objects with validation.
 */
public final class PageUtils {
    
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 100;
    
    private PageUtils() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    /**
     * Create a Pageable with default values.
     */
    public static Pageable createPageable() {
        return PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE);
    }
    
    /**
     * Create a Pageable with specified page and size.
     * Validates and adjusts the parameters if necessary.
     */
    public static Pageable createPageable(int page, int size) {
        int validatedPage = Math.max(0, page);
        int validatedSize = Math.min(Math.max(1, size), MAX_SIZE);
        return PageRequest.of(validatedPage, validatedSize);
    }
    
    /**
     * Create a Pageable with specified page, size, and sort.
     */
    public static Pageable createPageable(int page, int size, Sort sort) {
        int validatedPage = Math.max(0, page);
        int validatedSize = Math.min(Math.max(1, size), MAX_SIZE);
        return PageRequest.of(validatedPage, validatedSize, sort);
    }
    
    /**
     * Create a Sort object for ascending order.
     */
    public static Sort createSortAsc(String... properties) {
        return Sort.by(Sort.Direction.ASC, properties);
    }
    
    /**
     * Create a Sort object for descending order.
     */
    public static Sort createSortDesc(String... properties) {
        return Sort.by(Sort.Direction.DESC, properties);
    }
    
    /**
     * Get the maximum allowed page size.
     */
    public static int getMaxSize() {
        return MAX_SIZE;
    }
    
    /**
     * Get the default page size.
     */
    public static int getDefaultSize() {
        return DEFAULT_SIZE;
    }
}
