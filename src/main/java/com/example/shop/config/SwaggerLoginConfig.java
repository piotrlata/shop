package com.example.shop.config;

//import com.google.common.collect.Multimap;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import springfox.documentation.builders.ApiListingBuilder;
//import springfox.documentation.builders.OperationBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiDescription;
//import springfox.documentation.service.ApiListing;
//import springfox.documentation.service.ResponseMessage;
//import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
//import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;
//import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
//import springfox.documentation.spring.web.scanners.ApiListingScanner;
//import springfox.documentation.spring.web.scanners.ApiListingScanningContext;
//import springfox.documentation.spring.web.scanners.ApiModelReader;
//
//import java.util.Collections;
//import java.util.Set;
//
//@Configuration
//@Primary
//public class SwaggerLoginConfig extends ApiListingScanner {
//    private static final String LOGIN = "login";
//
//    public SwaggerLoginConfig(ApiDescriptionReader apiDescriptionReader, ApiModelReader apiModelReader, DocumentationPluginsManager pluginsManager) {
//        super(apiDescriptionReader, apiModelReader, pluginsManager);
//    }
//
//    @Override
//    public Multimap<String, ApiListing> scan(ApiListingScanningContext context) {
//        Multimap<String, ApiListing> scan = super.scan(context);
//        var operation = new OperationBuilder(new CachingOperationNameGenerator())
//                .method(HttpMethod.POST)
//                .uniqueId(LOGIN)
//                .parameters(Collections.singletonList(new ParameterBuilder()
//                        .name("body")
//                        .required(true)
//                        .description("body of request")
//                        .parameterType("body")
//                        .modelRef(new ModelRef("LoginDto"))
//                        .build()))
//                .responseMessages(Set.of(
//                        new ResponseMessage(200, "correct login",
//                                new ModelRef("SuccessfulLoginDto"), Collections.emptyMap(), Collections.emptyList()),
//                        new ResponseMessage(401, "incorrect login data",
//                                new ModelRef("String"), Collections.emptyMap(), Collections.emptyList())
//                ))
//                .consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
//                .summary(LOGIN)
//                .notes("here u can login")
//                .build();
//        var apiDescription = new ApiDescription(LOGIN, "/api/login", "here you can login", Collections.singletonList(operation), false);
//        scan.put("authentication", new ApiListingBuilder(context.getDocumentationContext().getApiDescriptionOrdering())
//                .apis(Collections.singletonList(apiDescription))
//                .description("jwt authentication")
//                .build());
//        return scan;
//    }
//}