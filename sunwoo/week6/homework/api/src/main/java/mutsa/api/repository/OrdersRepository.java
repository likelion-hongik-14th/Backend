package mutsa.api.repository;

import mutsa.api.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUsersId(Long usersId);

    Optional<Orders> findByIdAndUsersId(Long id, Long usersId);
}
