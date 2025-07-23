package com.example.demo.application.query;

import com.example.core.application.query.Query;
import com.example.demo.application.dto.UserDto;
import lombok.Data;

/**
 * Query for getting a user by ID.
 */
@Data
public class GetUserByIdQuery implements Query<UserDto> {
    
    private final Long userId;
}
