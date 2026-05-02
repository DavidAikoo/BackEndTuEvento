package com.tuevento.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TuEvento API")
                        .version("1.0.0")
                        .description("API REST para el Sistema de Gestión de Eventos Culturales")
                        .contact(new Contact()
                                .name("TuEvento")
                                .email("contacto@tuevento.com")));
    }
}
