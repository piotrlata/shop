package com.example.shop.mapper.impl;

import com.example.shop.domain.dao.User;
import com.example.shop.domain.dto.UserDto;
import com.example.shop.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User dtoToDao(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .id(userDto.getId())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .build();
    }

    @Override
    public UserDto daoToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
