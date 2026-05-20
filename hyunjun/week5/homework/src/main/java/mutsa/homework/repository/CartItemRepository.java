package mutsa.homework.repository;

import mutsa.homework.domain.Cart;
import mutsa.homework.domain.CartItem;
import mutsa.homework.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    
}
