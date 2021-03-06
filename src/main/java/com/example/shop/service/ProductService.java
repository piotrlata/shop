package com.example.shop.service;

import com.example.shop.domain.dao.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    @CachePut(cacheNames = "product", key = "#result.id")
    Product save(Product product, MultipartFile image);

    @CacheEvict(cacheNames = "product", key = "#id")
    void delete(Long id);

    @CachePut(cacheNames = "product", key = "#id")
    Product update(Product product, MultipartFile image, Long id);

    @Cacheable(cacheNames = "product", key = "#id")
    Product findProductById(Long id);

    Page<Product> getPage(Pageable pageable);
}