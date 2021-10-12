package com.example.shop.mapper;

import com.example.shop.domain.dao.User;
import com.example.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends AuditingMapper<User, UserDto> {
    User dtoToDao(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto daoToDto(User user);
}
