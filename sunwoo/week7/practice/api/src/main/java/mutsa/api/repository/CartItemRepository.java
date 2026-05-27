package mutsa.api.repository;

import mutsa.api.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndCartUsersId(Long id, Long usersId);
}
