package app.kubix.onboarding.auditing;

import app.kubix.onboarding.entity.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public class ApplicationAuditAware implements AuditorAware<UUID> {
	@Override
	public Optional<UUID> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}

		UserEntity userPrincipal = (UserEntity) authentication.getPrincipal();
		return Optional.ofNullable(userPrincipal.getUserId());
	}
}
