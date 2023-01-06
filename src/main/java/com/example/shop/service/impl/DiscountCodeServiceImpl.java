package com.example.shop.service.impl;

import com.example.shop.domain.dao.DiscountCode;
import com.example.shop.repository.DiscountCodeRepository;
import com.example.shop.service.DiscountCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class DiscountCodeServiceImpl implements DiscountCodeService {
    private final DiscountCodeRepository discountCodeRepository;

    @Override
    public DiscountCode save(DiscountCode discountCode) {
        return discountCodeRepository.save(discountCode);
    }

    @Override
    public DiscountCode findByName(String name) {
        return discountCodeRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public DiscountCode update(DiscountCode discountCode, String name) {
        var discountCodeDb = findByName(name);
        discountCodeDb.setName(discountCode.getName());
        discountCodeDb.setAmount(discountCode.getAmount());
        discountCodeDb.setCounter(discountCode.getCounter());
        return discountCodeDb;
    }

    @Override
    public void delete(Long id) {
        discountCodeRepository.deleteById(id);
    }
}