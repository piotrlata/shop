package com.example.shop.generic.strategy.file.impl;

import com.example.shop.generator.model.FileType;
import com.example.shop.generic.strategy.file.FileGeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PdfFileGeneratorStrategy implements FileGeneratorStrategy {
    @Override
    public FileType getType() {
        return FileType.PDF;
    }

    @Override
    public byte[] generateFile() {
        log.info("pdf");
        return new byte[0];
    }
}
