package com.example.shop.repository;

import com.example.shop.domain.dao.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    void deleteByProductIdAndUserId(Long productId, Long userId);

    void deleteByUserId(Long userId);

    List<Basket> findByUserId(Long userId);

    Optional<Basket> findByProductIdAndUserId(Long productId, Long userId);
}