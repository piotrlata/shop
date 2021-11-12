package com.example.shop.controller;

import com.example.shop.domain.dao.Basket;
import com.example.shop.domain.dto.ProductDto;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.service.BasketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
    @ApiOperation(value = "delete product from basket by id", authorizations = @Authorization(value = "JWT"))
    public void deleteProductFromBasket(@PathVariable Long id) {
        basketService.deleteProduct(id);
    }

    @DeleteMapping
    @ApiOperation(value = "delete all products from basket", authorizations = @Authorization(value = "JWT"))
    public void deleteAllFromBasket() {
        basketService.clearBasket();
    }

    @GetMapping
    @ApiOperation(value = "get all products", authorizations = @Authorization(value = "JWT"))
    public List<ProductDto> getAllProducts() {
        return productMapper.listDaoToListDto(basketService.getProducts());
    }

    @PostMapping
    @ApiOperation(value = "add product to basket", authorizations = @Authorization(value = "JWT"))
    public void addProductToBasket(@RequestBody ProductDto product) {
        basketService.addProduct(productMapper.dtoToDao(product));
    }

    @PutMapping
    @ApiOperation(value = "update product in basket", authorizations = @Authorization(value = "JWT"))
    public void updateProductInBasket(@RequestBody ProductDto product) {
        basketService.updateBasket(productMapper.dtoToDao(product));
    }
}
