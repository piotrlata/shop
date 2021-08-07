package com.example.shop.generator;

import com.example.shop.generator.model.FileType;
import com.example.shop.generator.strategy.GeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GeneratorFactory {
    private final List<GeneratorStrategy> generatorStrategyList;
    private Map<FileType, GeneratorStrategy> strategyMap;

    @PostConstruct
    void init() {
        strategyMap = generatorStrategyList.stream()
                .collect(Collectors.toMap(GeneratorStrategy::getFileType, Function.identity()));
    }
    public GeneratorStrategy getMapValue(FileType fileType) {
        return strategyMap.get(fileType);
    }
}
