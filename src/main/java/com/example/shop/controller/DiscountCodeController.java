package com.example.shop.controller;

import com.example.shop.domain.dto.DiscountCodeDto;
import com.example.shop.mapper.DiscountCodeMapper;
import com.example.shop.service.DiscountCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discount_codes")
@RequiredArgsConstructor
public class DiscountCodeController {
    private final DiscountCodeService discountCodeService;
    private final DiscountCodeMapper discountCodeMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "create discount code", security = {@SecurityRequirement(name = "bearer")})
    public DiscountCodeDto saveDiscountCode(@RequestBody DiscountCodeDto discountCode) {
        return discountCodeMapper.daoToDto(discountCodeService.save(discountCodeMapper.dtoToDao(discountCode)));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "get discount code by name", security = {@SecurityRequirement(name = "bearer")})
    public DiscountCodeDto discountCodeByName(@RequestParam String name) {
        return discountCodeMapper.daoToDto(discountCodeService.findByName(name));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "update discount code", security = {@SecurityRequirement(name = "bearer")})
    public DiscountCodeDto updateDiscountCode(@RequestBody DiscountCodeDto discountCode, @RequestParam String name) {
        return discountCodeMapper.daoToDto(discountCodeService.update(discountCodeMapper.dtoToDao(discountCode), name));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "delete discount code", security = {@SecurityRequirement(name = "bearer")})
    public void deleteDiscountCode(@PathVariable Long id) {
        discountCodeService.delete(id);
    }
}