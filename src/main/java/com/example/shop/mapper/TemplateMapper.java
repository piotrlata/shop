package com.example.shop.mapper;

import com.example.shop.domain.dao.Template;
import com.example.shop.domain.dto.TemplateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper {
    Template dtoToDao(TemplateDto templateDto);

    TemplateDto daoToDto(Template template);
}
