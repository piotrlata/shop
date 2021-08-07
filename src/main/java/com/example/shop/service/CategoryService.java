package com.example.shop.service;

import com.example.shop.domain.dao.Category;
import com.example.shop.domain.dao.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category save(Category category);

    void delete(Long id);

    Category update(Category category, Long id);

    Category findCategoryById(Long id);

    Page<Category> getPage(Pageable pageable);
}
