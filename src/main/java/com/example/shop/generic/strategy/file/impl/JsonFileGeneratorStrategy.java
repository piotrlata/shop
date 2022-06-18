package com.example.shop.generic.strategy.file.impl;

import com.example.shop.generator.model.FileType;
import com.example.shop.generic.strategy.file.FileGeneratorStrategy;
import com.example.shop.repository.UserRepository;
import com.example.shop.security.SecurityUtils;
import com.example.shop.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonFileGeneratorStrategy implements FileGeneratorStrategy {
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public FileType getType() {
        return FileType.JSON;
    }

    @Override
    public byte[] generateFile() throws Exception {
        byte[] byteArrayFile = objectMapper.writeValueAsBytes(userRepository.findAll());
        emailService.sendEmail(SecurityUtils.getCurrentUserEmail(), "reportFile", Collections.emptyMap(), byteArrayFile, "report.json");
        return byteArrayFile;
    }
}
