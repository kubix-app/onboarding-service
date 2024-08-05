package app.kubix.onboarding.service.token;

import app.kubix.onboarding.enums.TokenType;
import app.kubix.onboarding.entity.Token;
import app.kubix.onboarding.entity.UserEntity;
import app.kubix.onboarding.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final TokenRepository tokenRepository;

	@Override
	public Optional<Token> findByUserAndRevokedAndExpired(UserEntity user, boolean revoked, boolean expired) {
		return tokenRepository.findByUserAndRevokedAndExpired(user, revoked, expired);
	}

	@Override
	public Integer removeAllByUser(UserEntity user) {
		return tokenRepository.removeAllByUser(user);
	}

	@Override
	public void save(Token storeToken) {
		tokenRepository.save(storeToken);
	}

	@Override
	public void saveUserToken(UserEntity user, String jwtToken) {
		Token token = new Token(jwtToken, TokenType.BEARER, false, false, user);
		tokenRepository.save(token);
	}

	@Override
	public void saveExpiredAndRevokedToken(Token token) {
		expiredToken(List.of(token));
	}

	@Override
	public Optional<Token> findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

	@Override
	public Boolean isTokenValid(String token) {
		return findByToken(token).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
	}

	@Override
	public void revokeAllUserTokens(UserEntity user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
		if (validUserTokens.isEmpty())
			return;
		expiredToken(validUserTokens);
	}

	private void expiredToken(List<Token> validUserTokens) {
		validUserTokens.forEach(token -> {
			token.setExpired(Boolean.TRUE);
			token.setRevoked(Boolean.TRUE);
		});
		tokenRepository.saveAll(validUserTokens);
	}
}
