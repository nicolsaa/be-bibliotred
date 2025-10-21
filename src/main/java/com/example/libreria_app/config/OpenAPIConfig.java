package com.example.libreria_app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Librería API",
        version = "1.0.0",
        description = "Documentación de la API"))
public class OpenAPIConfig {
}
