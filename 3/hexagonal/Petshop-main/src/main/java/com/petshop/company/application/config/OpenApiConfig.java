package com.petshop.company.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI petShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PetShop API")
                        .description("API para gestão de pet shop - clientes, produtos e pedidos")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("PetShop Team")
                                .email("contato@petshop.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
