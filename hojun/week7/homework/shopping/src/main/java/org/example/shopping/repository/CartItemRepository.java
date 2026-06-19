package org.example.shopping.repository;

import org.example.shopping.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByIdAndCartId(Long cartItemId, Long cartId);

}
