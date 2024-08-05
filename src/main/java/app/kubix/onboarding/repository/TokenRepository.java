package app.kubix.onboarding.repository;

import app.kubix.onboarding.entity.Token;
import app.kubix.onboarding.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

	@Query(value = """
			select t from Token t inner join UserEntity u\s
			on t.user.id = u.id\s
			where u.id = :id and (t.expired = false or t.revoked = false)\s
			""")
	List<Token> findAllValidTokenByUser(UUID id);

	Optional<Token> findByToken(String token);

	Optional<Token> findByUserAndRevokedAndExpired(UserEntity user, boolean revoked, boolean expired);

	@Transactional
	Integer removeAllByUser(UserEntity user);
}
