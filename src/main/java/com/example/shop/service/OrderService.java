package com.example.shop.service;

import com.example.shop.domain.OrderStatus;
import javassist.NotFoundException;

public interface OrderService {
    void createOrder();

    void deleteOrder(Long orderDetailsId);

    void changeOrderStatus(Long orderDetailsId, OrderStatus status) throws NotFoundException;
}
