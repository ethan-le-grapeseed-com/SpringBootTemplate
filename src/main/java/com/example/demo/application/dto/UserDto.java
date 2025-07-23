package com.example.demo.application.dto;

import com.example.core.application.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO for user data transfer.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends BaseDto {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String fullName;
    private String createdAt;
    private String updatedAt;
    
    @Override
    public String toString() {
        return String.format("UserDto{id=%d, fullName='%s', email='%s'}", 
            id, fullName, email);
    }
}
