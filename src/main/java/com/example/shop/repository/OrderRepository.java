package com.example.shop.repository;

import com.example.shop.domain.dao.OrderClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderClient, Long> {
    Optional<OrderClient> findByOrderDetailsId(Long orderDetailsId);
}
