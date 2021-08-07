package com.example.shop.controller;

import com.example.shop.domain.dto.CategoryDto;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/categories")
@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/{id}")
    public CategoryDto categoryById(@PathVariable Long id) {
        return categoryMapper.daoToDto(categoryService.findCategoryById(id));
    }

    @PostMapping
    public CategoryDto saveCategory(@RequestBody CategoryDto category) {
        return categoryMapper.daoToDto(categoryService.save(categoryMapper.dtoToDao(category)));
    }

    @GetMapping
    public Page<CategoryDto> categoryPage(@RequestParam int page,@RequestParam int size){
        return categoryService.getPage(PageRequest.of(page, size)).map(categoryMapper::daoToDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@RequestBody CategoryDto category,@PathVariable Long id){
        return categoryMapper.daoToDto(categoryService.update(categoryMapper.dtoToDao(category),id));
    }
}
