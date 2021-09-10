package com.example.shop.controller.history;

import com.example.shop.domain.dto.CategoryDto;
import com.example.shop.domain.dto.ProductDto;
import com.example.shop.domain.dto.UserDto;
import com.example.shop.mapper.RevisionMapper;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history/users")
@RequiredArgsConstructor
public class HistoryController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RevisionMapper revisionMapper;

    @GetMapping("/{id}")
    public Page<UserDto> getUserHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return userRepository.findRevisions(id, PageRequest.of(page, size)).map(revisionMapper::toUserDto);
    }

    @GetMapping("/{id}")
    public Page<ProductDto> getProductHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return productRepository.findRevisions(id, PageRequest.of(page, size)).map(revisionMapper::toProductDto);
    }

    @GetMapping("/{id}")
    public Page<CategoryDto> getCategoryHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return categoryRepository.findRevisions(id, PageRequest.of(page, size)).map(revisionMapper::toCategoryDto);
    }
}