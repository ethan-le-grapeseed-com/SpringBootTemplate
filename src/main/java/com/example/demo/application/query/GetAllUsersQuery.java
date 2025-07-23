package com.example.demo.application.query;

import com.example.core.application.query.Query;
import com.example.demo.application.dto.UserDto;

import java.util.List;

/**
 * Query to get all users.
 */
public class GetAllUsersQuery implements Query<List<UserDto>> {
    // No parameters needed for getting all users
}
