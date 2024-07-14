package app.kubix.onboarding.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(description = "OpenApi documentation for Kubix Portal", title = "Kubix Portal", version = "1.0"), servers = {
		@Server(description = "DEV ENV", url = "https://dev-api.kubix.app/api/v1"),
		@Server(description = "Local ENV", url = "http://localhost:8080/api/v1")})
@Configuration
public class OpenApiConfig {
	@Bean
	public GroupedOpenApi indexApi() {
		return GroupedOpenApi.builder().group("OnBoarding Service").packagesToScan("app.kubix.mainportal.controller")
				.pathsToMatch("/board/**").build();
	}
}
