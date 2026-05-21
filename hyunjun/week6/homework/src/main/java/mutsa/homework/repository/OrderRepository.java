package mutsa.homework.repository;

import mutsa.homework.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT o FROM Order o " +
            "JOIN FETCH o.orderItems oi " +
            "JOIN FETCH oi.product " +
            "WHERE o.user.id = :userId " +
            "ORDER BY o.createdAt DESC")
    List<Order> findAllWithItemsByUserId(@Param("userId") Long userId);
}
