package com.example.shop.config;

import com.example.shop.domain.dto.LoginDto;
import com.example.shop.domain.dto.SuccessfulLoginDto;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(typeResolver.resolve(LoginDto.class))
                .additionalModels(typeResolver.resolve(SuccessfulLoginDto.class))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.shop.controller"))
                .build();
    }
}
