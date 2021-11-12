package com.example.shop.service;

import com.example.shop.domain.dao.Basket;
import com.example.shop.domain.dao.Product;

import java.util.List;

public interface BasketService {
    void addProduct(Product product);

    void deleteProduct(Long productId);

    void updateBasket(Product product);

    void clearBasket();

    List<Product> getProducts();

    List<Basket> getBaskets();
}
