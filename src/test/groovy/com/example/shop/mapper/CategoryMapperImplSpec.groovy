package com.example.shop.mapper

import com.example.shop.domain.dao.Category
import com.example.shop.domain.dto.CategoryDto
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

class CategoryMapperImplSpec extends Specification {
    def categoryMapper = new CategoryMapperImpl()

    def 'should map dao to dto without auditing'() {
        given:
        def context = Mock(SecurityContext)
        SecurityContextHolder.setContext(context)
        def authentication = Mock(Authentication)
        context.getAuthentication() >> authentication
        authentication.getAuthorities() >> [new SimpleGrantedAuthority("USER")]
        def category = new Category(id: 1, name: "qwe", description: "asdqwe")

        when:
        def result = categoryMapper.daoToDto(category)

        then:
        result.id == category.id
        result.name == category.name
        result.description == category.description
        result.createdBy == null
        result.createdDate == null
        result.lastModifiedBy == null
        result.lastModifiedDate == null
    }

    def 'should map dao to dto with auditing'() {

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
