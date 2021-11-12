package com.example.shop.service.impl;

import com.example.shop.domain.OrderStatus;
import com.example.shop.domain.dao.Basket;
import com.example.shop.domain.dao.OrderClient;
import com.example.shop.domain.dao.OrderDetails;
import com.example.shop.repository.OrderRepository;
import com.example.shop.service.BasketService;
import com.example.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BasketService basketService;

    @Override
    public void createOrder() {
        var baskets = basketService.getBaskets();
        if (baskets.isEmpty()) {
            throw new IllegalArgumentException("there is nothing in basket");
        }
        for (Basket basket : baskets) {
            var orderClient = new OrderClient();
            var orderDetails = new OrderDetails();
            orderDetails.setUser(basket.getUser());
            orderDetails.setOrderStatus(OrderStatus.ACCEPTED);
            orderClient.setProduct(basket.getProduct());
            orderClient.setQuantity(basket.getQuantity());
            orderClient.setOrderDetails(orderDetails);
            orderRepository.save(orderClient);
        }
    }

    @Override
    public void cancelOrder(Long orderDetailsId) {
        var optionalOrder = orderRepository.findByOrderDetailsId(orderDetailsId);
        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("there is no order with this number");
        }
        var orderClient = optionalOrder.get();
        var details = orderClient.getOrderDetails();
        if (details.getOrderStatus().equals(OrderStatus.SHIPPED)) {
            throw new IllegalArgumentException("order is already shipped");
        }
        details.setOrderStatus(OrderStatus.CANCELED);
        orderClient.setOrderDetails(details);
    }

    @Override
    public void changeOrderStatus(Long orderDetailsId, OrderStatus status) {
        var optionalOrder = orderRepository.findByOrderDetailsId(orderDetailsId);
        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("there is no order with this number");
        }
        var orderClient = optionalOrder.get();
        var details = orderClient.getOrderDetails();
        details.setOrderStatus(status);
        orderClient.setOrderDetails(details);
        if (details.getOrderStatus().equals(OrderStatus.SHIPPED)) {
            details.setDispatchDate(LocalDateTime.now());
        }
        orderRepository.save(orderClient);
    }
}
