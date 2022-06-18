package com.example.shop.controller;

import com.example.shop.domain.dto.CategoryDto;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/categories")
@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/{id}")
    @Operation(description = "save product", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public CategoryDto categoryById(@PathVariable Long id) {
        return categoryMapper.daoToDto(categoryService.findCategoryById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "save category", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public CategoryDto saveCategory(@RequestBody CategoryDto category) {
        return categoryMapper.daoToDto(categoryService.save(categoryMapper.dtoToDao(category)));
    }

    @GetMapping
    @Operation(description = "get category page", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public Page<CategoryDto> categoryPage(@RequestParam int page, @RequestParam int size) {
        return categoryService.getPage(PageRequest.of(page, size)).map(categoryMapper::daoToDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "delete category", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public void deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "update category", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public CategoryDto updateCategory(@RequestBody CategoryDto category, @PathVariable Long id) {
        return categoryMapper.daoToDto(categoryService.update(categoryMapper.dtoToDao(category), id));
    }
}
