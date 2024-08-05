package app.kubix.onboarding.service.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException {

		log.error(StringUtils.trimAllWhitespace("Responding with unauthorized error. Message - {}"),
				StringUtils.trimAllWhitespace(e.getMessage()));
		String responseToClient = "{\"code\":401,\"message\":\"" + e.getMessage() + "\"}";
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(responseToClient);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
