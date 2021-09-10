package com.example.shop.mapper;

import com.example.shop.domain.dao.Category;
import com.example.shop.domain.dao.Product;
import com.example.shop.domain.dao.User;
import com.example.shop.domain.dto.CategoryDto;
import com.example.shop.domain.dto.ProductDto;
import com.example.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.history.Revision;

@Mapper(componentModel = "spring")
public interface RevisionMapper {
    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.firstName", target = "firstName")
    @Mapping(source = "entity.lastName", target = "lastName")
    @Mapping(source = "entity.email", target = "email")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    UserDto toUserDto(Revision<Integer, User> userRevision);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.name", target = "name")
    @Mapping(source = "entity.price", target = "price")
    @Mapping(source = "entity.description", target = "description")
    @Mapping(source = "entity.quantity", target = "quantity")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    ProductDto toProductDto(Revision<Integer, Product> productRevision);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.name", target = "name")
    @Mapping(source = "entity.description", target = "description")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    CategoryDto toCategoryDto(Revision<Integer, Category> categoryRevision);
}