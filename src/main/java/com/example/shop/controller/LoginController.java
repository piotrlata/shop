package com.example.shop.controller;

import com.example.shop.domain.dto.LoginDto;
import com.example.shop.domain.dto.SuccessfulLoginDto;
import com.example.shop.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public SuccessfulLoginDto login(@RequestBody LoginDto loginDto) {
        return loginService.login(loginDto.getEmail(), loginDto.getPassword());
    }
}
