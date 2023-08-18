package com.company.proxyproject.config;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "token";
    private final OpenApiProperties openApiProperties;

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .protocols(Sets.newHashSet("http", "https"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.company.proxyproject.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                openApiProperties.getTitle(),
                openApiProperties.getDescription(),
                openApiProperties.getVersion(),
                "Terms of Service",
                new Contact(
                        openApiProperties.getContactName(),
                        openApiProperties.getContactEmail(),
                        openApiProperties.getContactUrl()),
                "License of Api",
                "Api License Url",
                Collections.emptyList());
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference(AUTHORIZATION_HEADER, authorizationScopes));
    }

    private ApiKey apiKey(){
        return new ApiKey(AUTHORIZATION_HEADER, "JWT", "header");
    }
}
