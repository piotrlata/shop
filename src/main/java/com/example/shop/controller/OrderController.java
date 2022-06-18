package com.example.shop.controller;

import com.example.shop.domain.OrderStatus;
import com.example.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @DeleteMapping("/{id}")
    @Operation(description = "delete order", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    public void deleteOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }

    @PostMapping
    @Operation(description = "create order", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    @PreAuthorize("isAuthenticated()")
    public void createOrder() {
        orderService.createOrder();
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "update order", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public void updateOrder(@RequestParam Long orderDetailsId, @RequestParam OrderStatus status) {
        orderService.changeOrderStatus(orderDetailsId, status);
    }
}