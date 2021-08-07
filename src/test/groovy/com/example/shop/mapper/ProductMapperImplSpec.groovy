package com.example.shop.mapper

import com.example.shop.domain.dao.Product
import com.example.shop.domain.dto.ProductDto
import spock.lang.Specification

class ProductMapperImplSpec extends Specification {
    def productMapper = new ProductMapperImpl()

    def 'should map dao to dto'() {
        given:
        def product = new Product()

        when:
        def result = productMapper.daoToDto(product)

        then:
        result.id == product.id
        result.name == product.name
        result.price == product.price
        result.description == product.description
        result.quantity == product.quantity
    }

    def 'should map dto to dao'() {
        given:
        def productDto = new ProductDto()

        when:
        def result = productMapper.dtoToDao(productDto)

        then:
        result.id == productDto.id
        result.name == productDto.name
        result.price == productDto.price
        result.description == productDto.description
        result.quantity == productDto.quantity
    }
}
