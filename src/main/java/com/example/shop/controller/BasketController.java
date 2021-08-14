package com.example.shop.controller;

import com.example.shop.domain.dto.ProductDto;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baskets")
@PreAuthorize("isAuthenticated()")
public class BasketController {
    private final BasketService basketService;
    private final ProductMapper productMapper;

    @DeleteMapping("/{id}")
    public void deleteProductFromBasket(@PathVariable Long id) {
        basketService.deleteProduct(id);
    }

    @DeleteMapping
    public void deleteAllFromBasket() {
        basketService.clearBasket();
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productMapper.listDaoToListDto(basketService.getProducts());
    }
    @PostMapping
    public void addProductToBasket(@RequestBody ProductDto product) {
        basketService.addProduct(productMapper.dtoToDao(product));
    }
    @PutMapping
    public void updateProductInBasket(@RequestBody ProductDto product) {
        basketService.updateBasket(productMapper.dtoToDao(product));
    }
}
