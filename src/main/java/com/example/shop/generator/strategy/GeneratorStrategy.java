package com.example.shop.generator.strategy;

import com.example.shop.generator.model.FileType;

public interface GeneratorStrategy {
    FileType getFileType();
    byte[] generateFile();
}
