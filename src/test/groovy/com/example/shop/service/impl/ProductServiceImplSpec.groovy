package com.example.shop.service.impl

import com.example.shop.domain.dao.Product
import com.example.shop.repository.ProductRepository
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

class ProductServiceImplSpec extends Specification {
    def productService
    def productRepository = Mock(ProductRepository)

    def setup() {
        productService = new ProductServiceImpl(productRepository)
    }

    def 'should save product'() {
        given:
        def product = new Product(id: 1L)

        when:
        productService.save(product)

        then:
        1 * productRepository.save(product)
        0 * _
    }

    def 'should find product by id'() {
        given:
        productRepository.findById(1) >> Optional.of(new Product(id: 2L))

        when:
        def result = productService.findProductById(1)

        then:
        result != null
    }

    def 'should not find product by id'() {
        given:
        productRepository.findById(1) >> Optional.empty()

        when:
        productService.findProductById(1)

        then:
        thrown EntityNotFoundException
    }

    def 'should delete product'() {
        when:
        productService.delete(1)

        then:
        1 * productRepository.deleteById(1)
        0 * _
    }

    def 'should update product'() {
        given:
        productRepository.findById(2) >> Optional.of(new Product(id: 1L))
        def product = new Product(name: 'qweasd', price: 12.23, description: 'qweasdzxc', quantity: 21)

        when:
        def result = productService.update(product, 2)

        then:
        result.name == product.name
        result.price == product.price
        result.description == product.description
        result.quantity == product.quantity
    }

    def 'should page products'() {
        given:
        def pageable = PageRequest.of(1, 1)

        when:
        productService.getPage(pageable)

        then:
        1 * productRepository.findAll(pageable)
        0 * _
    }
}