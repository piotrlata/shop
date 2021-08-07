package com.example.shop.mapper;

import com.example.shop.domain.dao.User;
import com.example.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.history.Revision;

@Mapper(componentModel = "spring")
public interface UserRevisionMapper {

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.firstName", target = "firstName")
    @Mapping(source = "entity.lastName", target = "lastName")
    @Mapping(source = "entity.email", target = "email")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    UserDto daoToDto(Revision<Integer, User> userRevision);
}