package com.example.shop.service.impl

import com.example.shop.domain.dao.Category
import com.example.shop.repository.CategoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
        def category = new Category()

        when:
        categoryService.save(category)

        then:
        1 * categoryRepository.save(category)
        0 * _
    }

    def 'should find category by id'() {
        given:
        categoryRepository.findById(1) >> Optional.of(new Category())

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
        categoryRepository.deleteById(1)
    }

    def 'should update category'() {
        given:
        categoryRepository.findById(1) >> Optional.of(new Category())
        def category = new Category(name: 'phone', description: 'qweasdzxcasdqwe')

        when:
        def result = categoryService.update(category, 1)

        then:
        result.name == category.name
        result.description == category.description
    }

    def 'should page categories'() {
        given:
        def pageable = new PageRequest(1, 1, Sort.unsorted())

        when:
        categoryRepository.findAll(pageable)

        then:
        1 * categoryRepository.findAll(pageable)
        0 * _
    }
}
