package com.renthub.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI rentHubOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("RentHub AI API")

                        .description("AI Powered Universal Rental Marketplace Backend APIs")

                        .version("v1.0")

                        .contact(new Contact()
                                .name("RentHub AI Team")
                                .email("support@renthub.ai"))

                        .license(new License()
                                .name("Proprietary")))

                .externalDocs(new ExternalDocumentation()

                        .description("RentHub AI Documentation"));
    }
}