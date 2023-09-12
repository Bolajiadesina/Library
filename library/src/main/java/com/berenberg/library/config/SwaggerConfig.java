package com.berenberg.library.config;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springdoc.core.Constants.ALL_PATTERN;

/**
 * @author Bolaji
 * created on 26/08/2023
 */

@Configuration
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "BearerAuth", scheme = "bearer", in = SecuritySchemeIn.HEADER)

public class SwaggerConfig {


    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Library Loan")
                .pathsToMatch(ALL_PATTERN)
                .addOpenApiCustomiser(openApi ->
                        openApi.info(
                                new Info()
                                        .title("LIBRARY")
                                        .version("1.0")
                                        .description("Library")
                                        .license(new License().name("Apache 2.0"))
                                        .contact(new Contact().name("Berenberg").email("Berenberg").url("Berenberg"))
                        )
                )
                .build();
    }
}
