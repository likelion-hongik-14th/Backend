package mutsa.api.repository;

import mutsa.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // 소셜 제공자(provider)와 고유 ID(providerId)로 가입된 유저인지 조회하는 메서드
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}