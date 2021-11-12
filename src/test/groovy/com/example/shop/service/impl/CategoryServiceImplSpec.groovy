package com.example.shop.service.impl

import com.example.shop.domain.dao.Category
import com.example.shop.repository.CategoryRepository
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

class CategoryServiceImplSpec extends Specification {
    def categoryService
    def categoryRepository = Mock(CategoryRepository)

    def setup() {
        categoryService = new CategoryServiceImpl(categoryRepository)
    }

    def 'should save category'() {
        given:
        def category = new Category(id: 1)

        when:
        categoryService.save(category)

        then:
        1 * categoryRepository.save(category)
        0 * _
    }

    def 'should find category by id'() {
        given:
        categoryRepository.findById(1) >> Optional.of(new Category(id: 1))

        when:
        def result = categoryService.findCategoryById(1)

        then:
        result != null
    }

    def 'should not find category by id'() {
        given:
        categoryRepository.findById(1) >> Optional.empty()

        when:
        categoryService.findCategoryById(1)

        then:
        thrown EntityNotFoundException
    }

    def 'should delete category by id'() {
        when:
        categoryService.delete(1)

        then:
        1 * categoryRepository.deleteById(1)
        0 * _
    }

    def 'should update category'() {
        given:
        categoryRepository.findById(1) >> Optional.of(new Category(id: 1))
        def category = new Category(name: 'phone', description: 'qweasdzxcasdqwe')

        when:
        def result = categoryService.update(category, 1)

        then:
        result.name == category.name
        result.description == category.description
    }

    def 'should page categories'() {
        given:
        def pageable = PageRequest.of(1, 1)

        when:
        categoryService.getPage(pageable)

        then:
        1 * categoryRepository.findAll(pageable)
        0 * _
    }
}
