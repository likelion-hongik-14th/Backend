package mutsa.hw5.repository;

import jakarta.persistence.LockModeType;
import mutsa.hw5.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 주문 시 동시성 문제 방지를 위해 행 수준 락 (수량이 1개 남았을 때 두 사람이 동시에 주문하면 둘 다 승인되는 문제 해결)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.productId = :productId")
    Optional<Product> findByIdForUpdate(@Param("productId") Long productId);
}
