package app.kubix.onboarding.service.token;

import app.kubix.onboarding.entity.Token;
import app.kubix.onboarding.entity.UserEntity;

import java.util.Optional;

public interface TokenService {

	Optional<Token> findByUserAndRevokedAndExpired(UserEntity user, boolean revoked, boolean expired);

	Integer removeAllByUser(UserEntity user);

	void save(Token token);

	void saveUserToken(UserEntity user, String jwtToken);

	void saveExpiredAndRevokedToken(Token token);

	Optional<Token> findByToken(String token);

	Boolean isTokenValid(String token);

	void revokeAllUserTokens(UserEntity user);
}
