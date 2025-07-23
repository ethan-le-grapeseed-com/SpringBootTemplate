package com.example.demo.application.mapper;

import com.example.demo.application.dto.UserDto;
import com.example.demo.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between User domain model and UserDto.
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    UserDto toDto(User user);
}
