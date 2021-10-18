package com.example.shop.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("!test")
public class FlywayConfig {
    public FlywayConfig(DataSource dataSource) {
        Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(dataSource)
                .load()
                .migrate();
    }
}
