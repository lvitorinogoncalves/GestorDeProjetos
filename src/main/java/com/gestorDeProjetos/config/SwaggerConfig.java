package com.gestorDeProjetos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Gestor de Projetos API",
                version = "1.0",
                description = "API para o sistema de gerenciamento de projetos, permitindo operações de CRUD para membros, pessoas e outras entidades.",
                contact = @Contact(name = "Suporte API", email = "suporte@exemplo.com"),
                license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT")
        ),
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = {
                @Server(url = "http://localhost:8080", description = "Servidor local"),
                @Server(url = "https://api.prod.exemplo.com", description = "Servidor de Produção")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/v1/**")
                .build();
    }

    @Bean
    public GroupedOpenApi membroApi() {
        return GroupedOpenApi.builder()
                .group("membros")
                .pathsToMatch("/v1/membros/**")
                .build();
    }

    @Bean
    public GroupedOpenApi pessoaApi() {
        return GroupedOpenApi.builder()
                .group("pessoas")
                .pathsToMatch("/v1/pessoas/**")
                .build();
    }

    @Bean
    public GroupedOpenApi projetosApi() {
        return GroupedOpenApi.builder()
                .group("projetos")
                .pathsToMatch("/v1/projetos/**")
                .build();
    }
}
