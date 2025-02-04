package team.eusha.lifewise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.eusha.lifewise.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByValue(String value);
}
