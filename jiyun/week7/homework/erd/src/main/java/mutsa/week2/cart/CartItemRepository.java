package mutsa.week2.cart;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart_Member_Id(Long memberId);

    boolean existsByCart_IdAndProduct_Id(Long cartId, Long productId);
}
