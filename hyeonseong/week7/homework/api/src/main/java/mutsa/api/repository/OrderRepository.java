package mutsa.api.repository;

import mutsa.api.domain.Order;
import mutsa.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findAllByUserOrderByOrderDateDesc(User user);
}
