package com.example.shop.generic;

import com.example.shop.generic.strategy.GenericStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenericFactory<K, V extends GenericStrategy<K>> {
    private final List<V> strategies;
    private Map<K, V> strategyMap;

    @PostConstruct
    void init() {
        strategyMap = strategies.stream()
                .collect(Collectors.toMap(GenericStrategy::getType, Function.identity()));
    }
    public V  getStrategyByType(K k) {
        return strategyMap.get(k);
    }
}
