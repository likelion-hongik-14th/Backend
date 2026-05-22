package mutsa.session5.Repository;

import mutsa.session5.Entity.Cart;
import mutsa.session5.Entity.CartItem;
import mutsa.session5.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
