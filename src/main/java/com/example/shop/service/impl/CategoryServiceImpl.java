package com.example.shop.service.impl;

import com.example.shop.domain.dao.Category;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Category update(Category category, Long id) {
        var categoryDb = findCategoryById(id);
        categoryDb.setDescription(category.getDescription());
        categoryDb.setName(category.getName());
        return categoryDb;
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("category " + id + " doesn't exist"));
    }

    @Override
    public Page<Category> getPage(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
}
