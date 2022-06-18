package com.example.shop.service;

import com.example.shop.domain.dto.SuccessfulLoginDto;

public interface LoginService {
    SuccessfulLoginDto login(String email, String password);
}
