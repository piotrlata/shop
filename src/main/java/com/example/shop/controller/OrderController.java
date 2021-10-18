package com.example.shop.controller;

import com.example.shop.domain.OrderStatus;
import com.example.shop.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @DeleteMapping("/{id}")
    @ApiOperation(value = "cancel order", authorizations = @Authorization(value = "JWT"))
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    public void deleteOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }

    @PostMapping
    @ApiOperation(value = "create order", authorizations = @Authorization(value = "JWT"))
    @PreAuthorize("isAuthenticated()")
    public void createOrder() {
        orderService.createOrder();
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "update details of order", authorizations = @Authorization(value = "JWT"))
    public void updateOrder(@RequestParam Long orderDetailsId, @RequestParam OrderStatus status) {
        orderService.changeOrderStatus(orderDetailsId, status);
    }
}