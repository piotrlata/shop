package com.example.shop.controller;

import com.example.shop.domain.dto.ProductDto;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(description = "delete product from basket", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public void deleteProductFromBasket(@PathVariable Long id) {
        basketService.deleteProduct(id);
    }

    @DeleteMapping
    @Operation(description = "delete all from", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public void deleteAllFromBasket() {
        basketService.clearBasket();
    }

    @GetMapping
    @Operation(description = "get all products", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public List<ProductDto> getAllProducts() {
        return productMapper.listDaoToListDto(basketService.getProducts());
    }

    @PostMapping
    @Operation(description = "add product to basket", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public void addProductToBasket(@RequestBody ProductDto product) {
        basketService.addProduct(productMapper.dtoToDao(product));
    }

    @PutMapping
    @Operation(description = "save product", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public void updateProductInBasket(@RequestBody ProductDto product) {
        basketService.updateBasket(productMapper.dtoToDao(product));
    }
}
