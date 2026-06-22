package mutsa.session5.Repository;

import mutsa.session5.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderId(Long orderId);
    Optional<Order> findByOrderIdAndMember_MemberId(Long orderId, Long memberId);
}
