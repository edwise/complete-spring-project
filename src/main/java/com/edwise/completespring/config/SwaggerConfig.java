package com.edwise.completespring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    public static final String BOOKS_API_GROUP = "books-api";

    @Bean
    public GroupedOpenApi booksApi() {
        return GroupedOpenApi.builder()
                .group(BOOKS_API_GROUP)
                .packagesToScan("com.edwise.completespring.controllers") // Asegúrate de que esta es la ruta correcta de tus controladores
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                              .title("Books API")
                              .description("Your book database!")
                              .termsOfService("http://en.wikipedia.org/wiki/Terms_of_service")
                              .contact(new Contact()
                                               .name("edwise")
                                               .url("https://github.com/edwise")
                                               .email("edwise.null@gmail.com"))
                              .license(new License()
                                               .name("Apache License Version 2.0")
                                               .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                              .version("2.0"))
                .addServersItem(new io.swagger.v3.oas.models.servers.Server().url("http://localhost:8080"));
    }
}
