package mutsa.mutsa_week5_hw.repository;

import mutsa.mutsa_week5_hw.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
