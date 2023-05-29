package com.gamereview.api.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@io.swagger.v3.oas.annotations.security.SecurityScheme(name = "BearerJWT", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", description = "Bearer Token para o projeto.")
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openApiConfiguration() {
        return new OpenAPI()
                .info(apiInfo())
                .components(components());
    }

    private Info apiInfo() {
        return new Info()
                .title("Game Review API")
                .description("Sistema para fazer coleção e review de jogos")
                .version("V1.0")
                .contact(contact());

    }

    private Components components() {
        return new Components().addSecuritySchemes("bearer-key", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization"));
    }

    private Contact contact() {
        return new Contact().email("lourenco.ricardo@gmail.com").name("Ricardo Lourenço").url("https://github.com/R-Lourenco1998");
    }
}
