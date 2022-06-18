package com.example.shop.controller;

import com.example.shop.domain.dto.ProductDto;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.service.ProductService;
import com.example.shop.validator.FileValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
@Validated
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{id}")
    @Operation(description = "get product", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public ProductDto productById(@PathVariable Long id) {
        return productMapper.daoToDto(productService.findProductById(id));
    }

    @Validated
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "save product", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public ProductDto saveProduct(@RequestPart @Valid ProductDto product, @RequestPart @FileValidation @Valid MultipartFile image) {
        return productMapper.daoToDto(productService.save(productMapper.dtoToDao(product), image));
    }

    @GetMapping
    @Operation(description = "get product page", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public Page<ProductDto> productPage(@RequestParam int page, @RequestParam int size) {
        return productService.getPage(PageRequest.of(page, size)).map(productMapper::daoToDto);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "delete product", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }

    @Validated
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "update product", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public ProductDto updateProduct(@RequestPart @Valid ProductDto product, @RequestPart(required = false) @FileValidation @Valid MultipartFile image, @PathVariable Long id) {
        return productMapper.daoToDto(productService.update(productMapper.dtoToDao(product), image, id));
    }
}
