package com.example.shop.service.impl;

import com.example.shop.domain.OrderStatus;
import com.example.shop.domain.dao.Basket;
import com.example.shop.domain.dao.OrderClient;
import com.example.shop.domain.dao.OrderDetails;
import com.example.shop.domain.dao.User;
import com.example.shop.repository.BasketRepository;
import com.example.shop.repository.OrderRepository;
import com.example.shop.service.OrderService;
import com.example.shop.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final UserService userService;

    @Override
    public void createOrder() {
        User currentUser = userService.getCurrentUser();
        List<Basket> baskets = basketRepository.findByUserId(currentUser.getId());
        if (baskets.isEmpty()) {
            throw new IllegalArgumentException("there is nothing in basket");
        }
        for (Basket basket : baskets) {
            OrderClient orderClient = new OrderClient();
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setUser(basket.getUser());
            orderDetails.setOrderStatus(OrderStatus.ACCEPTED);
            orderClient.setProduct(basket.getProduct());
            orderClient.setQuantity(basket.getQuantity());
            orderClient.setOrderDetails(orderDetails);
            orderRepository.save(orderClient);
        }
    }

    @Override
    public void deleteOrder(Long orderDetailsId) {
        orderRepository.deleteByOrderDetailsId(orderDetailsId);
    }

    @Override
    public void changeOrderStatus(Long orderDetailsId, OrderStatus status) throws NotFoundException {
        Optional<OrderClient> optionalOrder = orderRepository.findByOrderDetailsId(orderDetailsId);
        if (optionalOrder.isEmpty()) {
            throw new NotFoundException("there is no order with this number");
        }
        OrderClient orderClient = optionalOrder.get();
        OrderDetails details = orderClient.getOrderDetails();
        details.setOrderStatus(status);
        orderClient.setOrderDetails(details);
        if (details.getOrderStatus().equals(OrderStatus.SHIPPED)) {
            details.setDispatchDate(LocalDateTime.now());
        }
        orderRepository.save(orderClient);
    }
}
