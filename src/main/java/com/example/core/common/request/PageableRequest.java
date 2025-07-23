package com.example.core.common.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Base request class for paginated queries.
 * Provides common pagination parameters.
 */
@Data
public class PageableRequest {
    
    @NotNull(message = "Page number cannot be null")
    private Integer page = 0;
    
    @NotNull(message = "Page size cannot be null")
    private Integer size = 20;
    
    private String sortBy;
    private String sortDirection = "ASC";
    
    /**
     * Get validated page number (minimum 0).
     */
    public int getValidatedPage() {
        return Math.max(0, page != null ? page : 0);
    }
    
    /**
     * Get validated page size (between 1 and 100).
     */
    public int getValidatedSize() {
        if (size == null) {
            return 20;
        }
        return Math.min(Math.max(1, size), 100);
    }
    
    /**
     * Check if sorting is requested.
     */
    public boolean hasSorting() {
        return sortBy != null && !sortBy.trim().isEmpty();
    }
    
    /**
     * Check if sort direction is descending.
     */
    public boolean isDescending() {
        return "DESC".equalsIgnoreCase(sortDirection);
    }
}
