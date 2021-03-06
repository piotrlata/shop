package com.example.shop.mapper;

import com.example.shop.domain.dao.Category;
import com.example.shop.domain.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends AuditingMapper<Category, CategoryDto> {
    Category dtoToDao(CategoryDto categoryDto);

    CategoryDto daoToDto(Category category);
}
