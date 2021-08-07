package com.example.shop.mapper;

import com.example.shop.domain.dao.User;
import com.example.shop.domain.dto.UserDto;

public interface UserMapper {
    User dtoToDao(UserDto userDto);
    UserDto daoToDto(User user);
}
