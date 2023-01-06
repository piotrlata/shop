package com.example.shop.service;

import com.example.shop.domain.dao.DiscountCode;

public interface DiscountCodeService {
    DiscountCode save(DiscountCode discountCode);

    DiscountCode findByName(String name);

    DiscountCode update(DiscountCode discountCode, String name);

    void delete(Long id);
}