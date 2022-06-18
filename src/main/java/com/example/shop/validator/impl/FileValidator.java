package com.example.shop.validator.impl;

import com.example.shop.validator.FileValidation;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<FileValidation, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || value.getOriginalFilename().endsWith(".jpg") || value.getOriginalFilename().endsWith(".png");
    }
}