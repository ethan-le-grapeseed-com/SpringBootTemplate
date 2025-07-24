package com.example.demo.presentation.controller;

import com.example.core.application.service.UseCaseDispatcher;
import com.example.core.common.response.ApiResponse;
import com.example.demo.application.command.CreateUserCommand;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.query.GetAllUsersQuery;
import com.example.demo.application.query.GetUserByIdQuery;
import com.example.demo.presentation.request.CreateUserRequest;
import com.example.demo.presentation.response.CreateUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for user operations.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management operations")
public class UserController {
    
    private final UseCaseDispatcher useCaseDispatcher;
    
    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        GetAllUsersQuery query = new GetAllUsersQuery();
        List<UserDto> users = useCaseDispatcher.dispatch(query);
        
        return ResponseEntity.ok(ApiResponse.success(users, "Users retrieved successfully"));
    }
    
    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(
        @Valid @RequestBody CreateUserRequest request) {
        
        CreateUserCommand command = new CreateUserCommand();
        command.setFirstName(request.getFirstName());
        command.setLastName(request.getLastName());
        command.setEmail(request.getEmail());
        
        Long userId = useCaseDispatcher.dispatch(command);
        
        CreateUserResponse response = new CreateUserResponse(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(response, "User created successfully"));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        GetUserByIdQuery query = new GetUserByIdQuery(id);
        UserDto user = useCaseDispatcher.dispatch(query);
        
        return ResponseEntity.ok(ApiResponse.success(user, "User retrieved successfully"));
    }
}
