package mutsa.hw5.repository;

import mutsa.hw5.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    boolean existsByProduct_ProductId(Long productId);
}
