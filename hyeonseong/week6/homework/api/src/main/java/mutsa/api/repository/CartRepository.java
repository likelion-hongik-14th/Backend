package mutsa.api.repository;

import mutsa.api.domain.User;
import mutsa.api.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
