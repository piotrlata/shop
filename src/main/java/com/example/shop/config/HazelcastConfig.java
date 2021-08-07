package com.example.shop.config;

import com.example.shop.domain.dao.Product;
import com.hazelcast.config.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching
@Profile("!test")
public class HazelcastConfig {
    @Bean
    public Config configHazelcast() {
        Config config = new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig()
                        .setName("product")
                        .setEvictionConfig(new EvictionConfig()
                                .setEvictionPolicy(EvictionPolicy.LFU)
                                .setSize(10)
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE))
                        .setTimeToLiveSeconds(60 * 60 * 24));
        config.getSerializationConfig().addDataSerializableFactory(1,id -> id==1?new Product():null);
        return config;
    }
}
