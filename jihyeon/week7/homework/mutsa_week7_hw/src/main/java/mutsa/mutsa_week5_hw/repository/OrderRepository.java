package mutsa.mutsa_week5_hw.repository;

import mutsa.mutsa_week5_hw.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 특정 회원의 주문 목록 조회
    List<Order> findByMemberId(Long memberId);
}
