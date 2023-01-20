package com.gamereview.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openApiConfiguration(){
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Game Review API")
                .description("Sistema para fazer coleção e review de jogos")
                .version("V1.0")
                .contact(contact());

    }

    private Contact contact() {
        return new Contact().email("lourenco.ricardo@gmail.com").name("Ricardo Lourenço").url("https://github.com/R-Lourenco1998");
    }
}
