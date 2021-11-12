package com.example.shop.service.impl

import com.example.shop.domain.OrderStatus
import com.example.shop.domain.dao.*
import com.example.shop.repository.OrderRepository
import com.example.shop.service.BasketService
import spock.lang.Specification

class OrderServiceImplSpec extends Specification {
    def orderService
    def orderRepository = Mock(OrderRepository)
    def basketService = Mock(BasketService)

    def setup() {
        orderService = new OrderServiceImpl(orderRepository, basketService)
    }

    def 'should cancel order'() {
        given:
        def orderDetailsId = 1L

        when:
        orderService.cancelOrder(orderDetailsId)

        then:
        1 * orderRepository.findByOrderDetailsId(orderDetailsId) >> Optional.of(new OrderClient(orderDetails: new OrderDetails(orderStatus: OrderStatus.ACCEPTED)))
        0 * _
    }

    def 'should not cancel order because order is shipped'() {
        given:
        def orderDetailsId = 1L
        orderRepository.findByOrderDetailsId(orderDetailsId) >> Optional.of(new OrderClient(orderDetails: new OrderDetails(orderStatus: OrderStatus.SHIPPED)))

        when:
        orderService.cancelOrder(orderDetailsId)

        then:
        thrown IllegalArgumentException
    }

    def 'should not cancel order because it is empty'() {
        given:
        def orderDetailsId = 1L
        orderRepository.findByOrderDetailsId(orderDetailsId) >> Optional.empty()

        when:
        orderService.cancelOrder(orderDetailsId)

        then:
        thrown IllegalArgumentException
    }

    def 'should not make order'() {
        given:
        basketService.getBaskets() >> Collections.emptyList()

        when:
        orderService.createOrder()

        then:
        thrown IllegalArgumentException
    }

    def 'should make order'() {
        when:
        orderService.createOrder()

        then:
        1 * basketService.getBaskets() >> [new Basket(user: new User(id: 1), product: new Product(id: 1), quantity: 1)]
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
        thrown IllegalArgumentException
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
