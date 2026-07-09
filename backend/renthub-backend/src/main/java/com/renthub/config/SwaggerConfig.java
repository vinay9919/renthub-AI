package com.renthub.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI rentHubOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()

                .info(
                        new Info()

                                .title("RentHub AI REST API")

                                .description("""
                                        AI Powered Rental Marketplace Backend

                                        Features

                                        • JWT Authentication
                                        • Booking
                                        • Payments
                                        • Reviews
                                        • Wishlist
                                        • Notifications
                                        • KYC
                                        • Admin Dashboard
                                        """)

                                .version("1.0.0")

                                .contact(
                                        new Contact()
                                                .name("RentHub AI Team")
                                                .email("support@renthub.ai"))

                                .license(
                                        new License()
                                                .name("MIT")))

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName))

                .schemaRequirement(
                        securitySchemeName,

                        new SecurityScheme()

                                .name(securitySchemeName)

                                .type(SecurityScheme.Type.HTTP)

                                .scheme("bearer")

                                .bearerFormat("JWT"))

                .externalDocs(
                        new ExternalDocumentation()
                                .description("RentHub Documentation"));
    }
}