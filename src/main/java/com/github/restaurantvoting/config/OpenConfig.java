package com.github.restaurantvoting.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//https://sabljakovich.medium.com/adding-basic-auth-authorization-option-to-openapi-swagger-documentation-java-spring-95abbede27e9
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
    info = @Info(
            title = "REST API documentation",
            version = "1.0",
        description = """
                Spring Boot <a href='https://github.com/gluhov-d/restaurant-voting-application'>application</a>
                <p><b>Test credentials: </b><br>
                - user@yandex.ru / password <br>
                - admin@gmail.com / admin </p>
                """,
        contact = @Contact(url = "https://t.me/dmitriiGluhov", name = "Dmitrii Gluhov", email = "glukhov.d@gmail.com")
),
security = @SecurityRequirement(name = "basicAuth")
)
public class OpenConfig {

        @Bean
        public GroupedOpenApi api() {
            return GroupedOpenApi.builder()
                    .group("REST API")
                    .pathsToMatch("/api/**")
                    .build();
        }
}
