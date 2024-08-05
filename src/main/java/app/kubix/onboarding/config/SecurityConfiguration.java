package app.kubix.onboarding.config;

import app.kubix.onboarding.service.handler.RestAccessDeniedHandler;
import app.kubix.onboarding.service.handler.RestAuthenticationEntryPoint;
import app.kubix.onboarding.service.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	private static final String[] WHITE_LIST_URL = {"/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs.yaml",
			"/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/actuator",
			"/actuator/health", "/nginx_status/**"};
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Bean
	public RestAuthenticationEntryPoint authenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public RestAccessDeniedHandler accessDeniedHandler() {
		return new RestAccessDeniedHandler();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						req -> req.requestMatchers(WHITE_LIST_URL).permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
	}
}
