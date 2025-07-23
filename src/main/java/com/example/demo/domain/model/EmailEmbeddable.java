package com.example.demo.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * JPA Embeddable wrapper for Email value object.
 * This allows Email to be embedded in JPA entities.
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailEmbeddable {
    
    private String value;
    
    public EmailEmbeddable(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
