package com.example.shop.mapper;

import com.example.shop.domain.dao.DiscountCode;
import com.example.shop.domain.dto.DiscountCodeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountCodeMapper {
    DiscountCode dtoToDao(DiscountCodeDto discountCodeDto);

    DiscountCodeDto daoToDto(DiscountCode discountCode);
}