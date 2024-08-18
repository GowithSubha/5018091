package com.example.Bookstore.config;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springdoc.core.OpenApi;
//import org.springdoc.core.SwaggerUiConfigParameters;
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {

        String securityScheme="bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securityScheme)
                )
                .components(new Components()
                        .addSecuritySchemes(securityScheme,
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
                                        .name("Authorization"))
                )
                .info(new Info().title("Bookstore API")
                        .description("SpringShop API implemented with Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Bookstore Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }




    
}
