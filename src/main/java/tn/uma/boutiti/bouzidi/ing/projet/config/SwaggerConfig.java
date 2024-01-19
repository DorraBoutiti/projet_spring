package tn.uma.boutiti.bouzidi.ing.projet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearer-token");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-token", securityScheme)
                )
                .addSecurityItem(securityRequirement)
                .info(new Info().title("My API").version("1.0"));
    }
}