package com.example.shop.mapper;

import com.example.shop.domain.dao.Role;
import com.example.shop.domain.dao.User;
import com.example.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper extends AuditingMapper<User, UserDto> {
    @Mapping(target = "roles", ignore = true)
    User dtoToDao(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToNames")
    UserDto daoToDto(User user);

    @Named("rolesToNames")
    default List<String> rolesToNames(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
