package com.example.shop.controller;

import com.example.shop.domain.dto.EmailDto;
import com.example.shop.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/emails")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping
    public void sendEmail(@RequestBody EmailDto<Map<String, Object>> emailDto) {
        emailService.sendEmail(emailDto.getEmailReceiver(), emailDto.getTemplateName(), emailDto.getVariables());
    }
}
