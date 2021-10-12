package com.example.shop.service.impl

import com.example.shop.domain.OrderStatus
import com.example.shop.domain.dao.*
import com.example.shop.repository.BasketRepository
import com.example.shop.repository.OrderRepository
import com.example.shop.service.UserService
import javassist.NotFoundException
import spock.lang.Specification

class OrderServiceImplSpec extends Specification {
    def orderService
    def orderRepository = Mock(OrderRepository)
    def basketRepository = Mock(BasketRepository)
    def userService = Mock(UserService)

    def setup() {
        orderService = new OrderServiceImpl(orderRepository, basketRepository, userService)
    }

    def 'should delete order'() {
        given:
        def orderDetailsId = 1L

        when:
        orderService.deleteOrder(orderDetailsId)

        then:
        1 * orderRepository.deleteByOrderDetailsId(orderDetailsId)
        0 * _
    }

    def 'should not make order'() {
        given:
        userService.getCurrentUser() >> new User(id: 1)
        basketRepository.findByUserId(1) >> Collections.emptyList()

        when:
        orderService.createOrder()

        then:
        thrown IllegalArgumentException
    }

    def 'should make order'() {
        when:
        orderService.createOrder()

        then:
        1 * userService.getCurrentUser() >> new User(id: 1)
        1 * basketRepository.findByUserId(1) >> [new Basket(quantity: 1, product: new Product(quantity: 2), user: new User(id: 1))]
        1 * orderRepository.save(_)
        0 * _
    }

    def 'should change order status'() {
        given:
        def orderDetailsId = 1L
        def status = OrderStatus.IN_PROGRESS

        when:
        orderService.changeOrderStatus(orderDetailsId, status)

        then:
        1 * orderRepository.findByOrderDetailsId(orderDetailsId) >> Optional.of(new OrderClient(orderDetails: new OrderDetails(orderStatus: OrderStatus.ACCEPTED)))
        1 * orderRepository.save(_)
        0 * _
    }

    def 'should not change order status'() {
        given:
        def orderDetailsId = 1L
        def status = OrderStatus.IN_PROGRESS
        orderRepository.findByOrderDetailsId(orderDetailsId) >> Optional.empty()

        when:
        orderService.changeOrderStatus(orderDetailsId, status)

        then:
        thrown NotFoundException
    }

    def 'should change dispatch date if status is shipped'() {
        given:
        def orderDetailsId = 1L
        def status = OrderStatus.SHIPPED

        when:
        def result = orderService.changeOrderStatus(orderDetailsId, status)

        then:
        1 * orderRepository.findByOrderDetailsId(orderDetailsId) >> Optional.of(new OrderClient(orderDetails: new OrderDetails(orderStatus: OrderStatus.ACCEPTED)))
        1 * orderRepository.save(_)
        0 * _
    }
}
