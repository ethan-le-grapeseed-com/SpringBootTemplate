package com.example.demo.application.query;

import com.example.core.application.query.QueryHandler;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.mapper.UserMapper;
import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Query handler for getting all users.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllUsersQueryHandler implements QueryHandler<GetAllUsersQuery, List<UserDto>> {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    @Override
    public List<UserDto> handle(GetAllUsersQuery query) {
        log.debug("Handling GetAllUsersQuery");
        
        List<User> users = userRepository.findAll();
        
        log.debug("Found {} users", users.size());
        
        return users.stream()
            .map(userMapper::toDto)
            .collect(Collectors.toList());
    }
}
