package com.example.shop.generic.strategy.file;

import com.example.shop.generator.model.FileType;
import com.example.shop.generic.strategy.GenericStrategy;

public interface FileGeneratorStrategy extends GenericStrategy<FileType> {
    byte[] generateFile();
}
