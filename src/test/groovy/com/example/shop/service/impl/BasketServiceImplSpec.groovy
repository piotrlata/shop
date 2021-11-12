package com.example.shop.service.impl

import com.example.shop.domain.dao.Basket
import com.example.shop.domain.dao.Product
import com.example.shop.domain.dao.User
import com.example.shop.repository.BasketRepository
import com.example.shop.service.ProductService
import com.example.shop.service.UserService
import spock.lang.Specification

class BasketServiceImplSpec extends Specification {
    def basketService
    def basketRepository = Mock(BasketRepository)
    def userService = Mock(UserService)
    def productService = Mock(ProductService)

    def setup() {
        basketService = new BasketServiceImpl(basketRepository, userService, productService)
    }

    def 'should delete product from basket'() {
        given:
        def productId = 1L

        when:
        basketService.deleteProduct(1)

        then:
        1 * userService.getCurrentUser() >> new User(id: 2)
        1 * basketRepository.deleteByProductIdAndUserId(productId, 2)
        0 * _
    }

    def 'should clear basket'() {
        when:
        basketService.clearBasket()

        then:
        1 * userService.getCurrentUser() >> new User(id: 1)
        1 * basketRepository.deleteByUserId(1)
        0 * _
    }

    def 'should add quantity to the product in basket'() {
        given:
        def product = new Product(id: 1, quantity: 2)

        when:
        basketService.addProduct(product)

        then:
        1 * userService.getCurrentUser() >> new User(id: 2)
        1 * productService.findProductById(product.id) >> new Product(id: 1, quantity: 3)
        1 * basketRepository.findByProductIdAndUserId(1, 2) >> Optional.of(new Basket(quantity: 1))
        1 * basketRepository.save(new Basket(quantity: 3))
        0 * _
    }

    def 'should not add quantity to product in basket'() {
        given:
        def product = new Product(id: 1, quantity: 2)
        userService.getCurrentUser() >> new User(id: 2)
        productService.findProductById(product.id) >> new Product(id: 1, quantity: 1)
        basketRepository.findByProductIdAndUserId(1, 2) >> Optional.of(new Basket(quantity: 2))

        when:
        basketService.addProduct(product)

        then:
        thrown IllegalArgumentException
    }

    def 'should not add product to basket'() {
        given:
        def product = new Product(id: 1, quantity: 2)
        userService.getCurrentUser() >> new User(id: 2)
        productService.findProductById(product.id) >> new Product(id: 1, quantity: 1)
        basketRepository.findByProductIdAndUserId(1, 2) >> Optional.empty()

        when:
        basketService.addProduct(product)

        then:
        thrown IllegalArgumentException
    }

    def 'should add product to basket'() {
        given:
        def product = new Product(id: 1, quantity: 2)

        when:
        basketService.addProduct(product)

        then:
        1 * userService.getCurrentUser() >> new User(id: 2)
        1 * productService.findProductById(product.id) >> new Product(id: 1, quantity: 3)
        1 * basketRepository.findByProductIdAndUserId(1, 2) >> Optional.empty()
        1 * basketRepository.save(_)
        0 * _
    }

    def 'should get products from basket'() {
        given:
        userService.getCurrentUser() >> new User(id: 1)
        basketRepository.findByUserId(1) >> [new Basket(quantity: 1, product: new Product(quantity: 2))]

        when:
        def result = basketService.getProducts()

        then:
        result == [new Product(quantity: 1)]
    }

    def 'should update basket'() {
        given:
        def product = new Product(id: 1, quantity: 2)

        when:
        basketService.updateBasket(product)

        then:
        1 * userService.getCurrentUser() >> new User(id: 2)
        1 * productService.findProductById(product.id) >> new Product(id: 1, quantity: 3)
        1 * basketRepository.findByProductIdAndUserId(1, 2) >> Optional.of(new Basket(quantity: 1))
        1 * basketRepository.save(_)
        0 * _
    }

    def 'should not update basket'() {
        given:
        def product = new Product(id: 1, quantity: 2)
        userService.getCurrentUser() >> new User(id: 2)
        productService.findProductById(product.id) >> new Product(id: 1, quantity: 1)
        basketRepository.findByProductIdAndUserId(1, 2) >> Optional.of(new Basket(quantity: 3))

        when:
        basketService.updateBasket(product)

        then:
        thrown IllegalArgumentException
    }

    def 'should get basket'() {
        when:
        basketService.getBaskets()

        then:
        1*userService.getCurrentUser() >> new User(id: 1L)
        1*basketRepository.findByUserId(1L)
        0*_
    }
}