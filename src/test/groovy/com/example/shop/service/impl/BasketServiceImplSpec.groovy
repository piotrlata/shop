package com.example.shop.service.impl


import com.example.shop.domain.dao.User
import com.example.shop.repository.BasketRepository
import com.example.shop.service.ProductService
import com.example.shop.service.UserService
import spock.lang.Specification

import java.time.LocalDateTime

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
        1 * userService.getCurrentUser() >> new User(2, '', '', '', '', LocalDateTime.now(), LocalDateTime.now(), '', '', [])
        1 * basketRepository.deleteByProductIdAndUserId(productId, 2)
        0 * _
    }

    def 'should clear basket'() {
        when:
        basketService.clearBasket()

        then:
        1 * userService.getCurrentUser() >> new User(1, '', '', '', '', LocalDateTime.now(), LocalDateTime.now(), '', '', [])
        1 * basketRepository.deleteByUserId(1)
        0 * _
    }

//    def 'should add product to basket'() {
//        given:
//        def product = new Product(1, '', 10.22, '', 1, new Category())
//
//        when:
//        basketService.addProduct(product)
//
//        then:
//        def user = userService.getCurrentUser() >> new User(2, '', '', '', '', LocalDateTime.now(), LocalDateTime.now(), '', '', [])
//        1*productService.findProductById(product.id)
//        1*basketRepository.findByProductIdAndUserId(product.id, 2) >> Optional.of(new Basket(3,user, product,1))
//
//    }
//    def 'should get products from basket'() {
//        when:
//        basketService.getProducts()
//
//        then:
//        1 * userService.getCurrentUser() >> new User(1, 'qwe', 'qwe', 'qwe', 'qwe', LocalDateTime.now(), LocalDateTime.now(), 'qwe', 'qwe', [])
//        1 * basketRepository.findByUserId(1) >> Optional.of()
//        0 * _
//    }
}