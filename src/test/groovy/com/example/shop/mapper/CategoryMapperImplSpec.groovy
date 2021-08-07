package com.example.shop.mapper

import com.example.shop.domain.dao.Category
import com.example.shop.domain.dao.Product
import com.example.shop.domain.dto.CategoryDto
import com.example.shop.domain.dto.ProductDto
import spock.lang.Specification

class CategoryMapperImplSpec extends Specification {
    def categoryMapper = new CategoryMapperImpl()

    def 'should map dao to dto'() {
        given:
        def category = new Category(id: 1, name: "qwe", description: "asdqwe")

        when:
        def result = categoryMapper.daoToDto(category)

        then:
        result.id == category.id
        result.name == category.name
        result.description == category.description
    }

    def 'should map dto to dao'() {
        given:
        def categoryDto = new CategoryDto(id: 1, name: "qwe", description: "asdqwe")

        when:
        def result = categoryMapper.dtoToDao(categoryDto)

        then:
        result.id == categoryDto.id
        result.name == categoryDto.name
        result.description == categoryDto.description
    }
}
