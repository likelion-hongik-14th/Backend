package mutsa.hw5.repository;

import mutsa.hw5.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);

    Optional<CartItem> findByItemIdAndCart_CartId(Long itemId, Long cartId);
}