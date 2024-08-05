package app.kubix.onboarding.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(description = "OpenApi documentation for Kubix Portal",
        title = "Kubix Portal", version = "1.0")
)
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "Authorization";
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme()
                        .name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi indexApi() {
        return GroupedOpenApi.builder().group("Onboarding Service").packagesToScan("app.kubix.onboarding.controller")
                .pathsToMatch("/auth/**").build();
    }
}
