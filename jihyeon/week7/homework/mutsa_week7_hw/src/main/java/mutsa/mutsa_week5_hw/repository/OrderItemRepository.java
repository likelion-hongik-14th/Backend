package mutsa.mutsa_week5_hw.repository;

import mutsa.mutsa_week5_hw.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
