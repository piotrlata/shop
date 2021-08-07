package com.example.shop.generator.strategy.impl;

import com.example.shop.generator.model.FileType;
import com.example.shop.generator.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CsvGeneratorImpl implements GeneratorStrategy {
    @Override
    public FileType getFileType() {
        return FileType.CSV;
    }

    @Override
    public byte[] generateFile() {
        log.info("csv");
        return new byte[0];
    }
}
