package com.example.shop.generic.strategy.file;

import com.example.shop.generator.model.FileType;
import com.example.shop.generic.strategy.GenericStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface FileGeneratorStrategy extends GenericStrategy<FileType> {
    byte[] generateFile() throws JsonProcessingException, Exception;
}
