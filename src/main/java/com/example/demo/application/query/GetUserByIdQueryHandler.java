package com.example.demo.application.query;

import com.example.core.application.query.QueryHandler;
import com.example.core.common.exception.EntityNotFoundException;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.mapper.UserMapper;
import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for GetUserByIdQuery.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetUserByIdQueryHandler implements QueryHandler<GetUserByIdQuery, UserDto> {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    @Override
    public UserDto handle(GetUserByIdQuery query) {
        User user = userRepository.findById(query.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User", query.getUserId()));
            
        return userMapper.toDto(user);
    }
}
