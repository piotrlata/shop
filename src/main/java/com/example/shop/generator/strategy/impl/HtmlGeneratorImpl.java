package com.example.shop.generator.strategy.impl;

import com.example.shop.generator.model.FileType;
import com.example.shop.generator.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HtmlGeneratorImpl implements GeneratorStrategy {
    @Override
    public FileType getFileType() {
        return FileType.HTML;
    }

    @Override
    public byte[] generateFile() {
        log.info("html");
        return new byte[0];
    }
}
