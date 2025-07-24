package com.example.core.common.mapper;

/**
 * Base mapper interface for converting between domain entities and DTOs.
 * This provides a common contract for all mappers in the application.
 * 
 * @param <E> Entity type
 * @param <D> DTO type
 */
public interface BaseMapper<E, D> {
    
    /**
     * Convert entity to DTO.
     */
    D toDto(E entity);
    
    /**
     * Convert DTO to entity.
     * Note: This might not always be applicable for all mappers.
     */
    default E toEntity(D dto) {
        throw new UnsupportedOperationException("toEntity conversion not implemented");
    }
}
