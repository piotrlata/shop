package com.example.shop.service;

import com.example.shop.domain.OrderStatus;

public interface OrderService {
    void createOrder();

    void cancelOrder(Long orderDetailsId);

    void changeOrderStatus(Long orderDetailsId, OrderStatus status);
}
