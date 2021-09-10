package com.example.shop.mapper;

import com.example.shop.domain.dao.Product;
import com.example.shop.domain.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product dtoToDao(ProductDto productDto);

    ProductDto daoToDto(Product product);

    List<ProductDto> listDaoToListDto(List<Product> productList);
}
