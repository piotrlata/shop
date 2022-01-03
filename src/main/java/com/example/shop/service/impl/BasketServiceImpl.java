package com.example.shop.service.impl;

import com.example.shop.domain.dao.Basket;
import com.example.shop.domain.dao.Product;
import com.example.shop.repository.BasketRepository;
import com.example.shop.service.BasketService;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public void addProduct(Product product) {
        var currentUser = userService.getCurrentUser();
        var productById = productService.findProductById(product.getId());
        var optionalBasket = basketRepository.findByProductIdAndUserId(productById.getId(), currentUser.getId());
        if (optionalBasket.isPresent()) {
            var basket = optionalBasket.get();
            var quantity = product.getQuantity() + basket.getQuantity();
            if (quantity > productById.getQuantity()) {
                throw new IllegalArgumentException("not enough quantity");
            }
            basket.setQuantity(quantity);
            basketRepository.save(basket);
        } else {
            if (product.getQuantity() > productById.getQuantity()) {
                throw new IllegalArgumentException("not enough quantity");
            }
            var basket = new Basket();
            basket.setProduct(product);
            basket.setQuantity(product.getQuantity());
            basket.setUser(currentUser);
            basketRepository.save(basket);
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        var currentUser = userService.getCurrentUser();
        basketRepository.deleteByProductIdAndUserId(productId, currentUser.getId());
    }

    @Override
    public void updateBasket(Product product) {
        var currentUser = userService.getCurrentUser();
        var productById = productService.findProductById(product.getId());
        basketRepository.findByProductIdAndUserId(product.getId(), currentUser.getId()).ifPresent(basket -> {
            var quantity = product.getQuantity() + basket.getQuantity();
            if (quantity > productById.getQuantity()) {
                throw new IllegalArgumentException("not enough quantity");
            }
            basket.setQuantity(quantity);
            basketRepository.save(basket);
        });
    }

    @Override
    public void clearBasket() {
        var currentUser = userService.getCurrentUser();
        basketRepository.deleteByUserId(currentUser.getId());
    }

    @Override
    public List<Product> getProducts() {
        var currentUser = userService.getCurrentUser();
        return basketRepository.findByUserId(currentUser.getId()).stream()
                .map(basket -> {
                    var product = basket.getProduct();
                    product.setQuantity(basket.getQuantity());
                    return product;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Basket> getBaskets() {
        var currentUser = userService.getCurrentUser();
        return basketRepository.findByUserId(currentUser.getId());
    }
}
