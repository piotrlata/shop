package com.example.shop.service.impl;

import com.example.shop.domain.dao.Basket;
import com.example.shop.domain.dao.Product;
import com.example.shop.domain.dao.User;
import com.example.shop.repository.BasketRepository;
import com.example.shop.service.BasketService;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public void addProduct(Product product) {
        User currentUser = userService.getCurrentUser();
        Product productById = productService.findProductById(product.getId());
        Optional<Basket> optionalBasket = basketRepository.findByProductIdAndUserId(productById.getId(), currentUser.getId());
        if (optionalBasket.isPresent()) {
            Basket basket = optionalBasket.get();
            int quantity = product.getQuantity() + basket.getQuantity();
            if (quantity > productById.getQuantity()) {
                throw new IllegalArgumentException("not enough quantity");
            }
            basket.setQuantity(quantity);
            basketRepository.save(basket);
        } else {
            if (product.getQuantity() > productById.getQuantity()) {
                throw new IllegalArgumentException("not enough quantity");
            }
            Basket basket = new Basket();
            basket.setProduct(product);
            basket.setQuantity(product.getQuantity());
            basket.setUser(currentUser);
            basketRepository.save(basket);
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        User currentUser = userService.getCurrentUser();
        basketRepository.deleteByProductIdAndUserId(productId, currentUser.getId());
    }

    @Override
    public void updateBasket(Product product) {
        User currentUser = userService.getCurrentUser();
        Product productById = productService.findProductById(product.getId());
        basketRepository.findByProductIdAndUserId(product.getId(), currentUser.getId()).ifPresent(basket -> {
            int quantity = product.getQuantity() + basket.getQuantity();
            if (quantity > productById.getQuantity()) {
                throw new IllegalArgumentException("not enough quantity");
            }
            basket.setQuantity(quantity);
            basketRepository.save(basket);
        });
    }

    @Override
    public void clearBasket() {
        User currentUser = userService.getCurrentUser();
        basketRepository.deleteByUserId(currentUser.getId());
    }

    @Override
    public List<Product> getProducts() {
        User currentUser = userService.getCurrentUser();
        return basketRepository.findByUserId(currentUser.getId()).stream()
                .map(basket -> {
                    Product product = basket.getProduct();
                    product.setQuantity(basket.getQuantity());
                    return product;
                })
                .collect(Collectors.toList());
    }
}
