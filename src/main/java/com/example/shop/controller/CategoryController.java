package com.example.shop.controller;

import com.example.shop.domain.dto.CategoryDto;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
    @ApiOperation(value = "get category by id")
    public CategoryDto categoryById(@PathVariable Long id) {
        return categoryMapper.daoToDto(categoryService.findCategoryById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "save category", authorizations = @Authorization(value = "JWT"))
    public CategoryDto saveCategory(@RequestBody CategoryDto category) {
        return categoryMapper.daoToDto(categoryService.save(categoryMapper.dtoToDao(category)));
    }

    @GetMapping
    @ApiOperation(value = "get category page")
    public Page<CategoryDto> categoryPage(@RequestParam int page, @RequestParam int size) {
        return categoryService.getPage(PageRequest.of(page, size)).map(categoryMapper::daoToDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "delete category by id", authorizations = @Authorization(value = "JWT"))
    public void deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "update category", authorizations = @Authorization(value = "JWT"))
    public CategoryDto updateCategory(@RequestBody CategoryDto category, @PathVariable Long id) {
        return categoryMapper.daoToDto(categoryService.update(categoryMapper.dtoToDao(category), id));
    }
}
