package app.kubix.onboarding.service;

import app.kubix.common.exception.MainPortalException;
import app.kubix.onboarding.dto.auth.AuthenticationRequest;
import app.kubix.onboarding.dto.auth.AuthenticationResponse;
import app.kubix.onboarding.entity.UserEntity;
import app.kubix.onboarding.exception.OnboardingExceptionCodes;
import app.kubix.onboarding.repository.UserRepository;
import app.kubix.onboarding.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;

	private final TokenService tokenService;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		checkEmailValid(request);
		Optional<UserEntity> userOpt = userRepository.findByEmail(request.email());
		checkUser(request, userOpt);
		checkUserCridentials(request, userOpt);

		var jwtToken = revokeTokenAndSaveToken(userOpt.get());
		var refreshToken = jwtService.generateRefreshToken(userOpt.get());
		return new AuthenticationResponse(userOpt.get().getFullname(), jwtToken, refreshToken);

	}

	private void checkUser(AuthenticationRequest request, Optional<UserEntity> userOpt) {
		if (userOpt.isEmpty()) {
			log.info("User: {} not found", request.email());
			throw new MainPortalException(OnboardingExceptionCodes.USER_NOT_FOUND);
		}
	}

	private void checkUserCridentials(AuthenticationRequest request, Optional<UserEntity> userOpt) {
		if (userOpt.get().getPassword() == null
				|| !passwordEncoder.matches(request.password(), userOpt.get().getPassword())) {
			log.info("User: {} credentials is not valid", request.email());
			throw new MainPortalException(OnboardingExceptionCodes.INVALID_CREDENTIALS);
		}
	}

	private void checkEmailValid(AuthenticationRequest request) {
		if (!EmailValidator.getInstance().isValid(request.email())) {
			log.info("User email: {} is not valid", request.email());
			throw new MainPortalException(OnboardingExceptionCodes.INVALID_EMAIL_EXCEPTION);
		}
	}

	private String revokeTokenAndSaveToken(UserEntity user) {
		var jwtToken = jwtService.generateToken(user);
		tokenService.revokeAllUserTokens(user);
		tokenService.saveUserToken(user, jwtToken);
		return jwtToken;
	}
}
