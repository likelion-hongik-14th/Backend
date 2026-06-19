package mutsa.hw5.repository;

import jakarta.persistence.LockModeType;
import mutsa.hw5.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // orderId + memberId로 조회 (PATCH용)
    Optional<Order> findByOrderIdAndMember_MemberId(Long orderId, Long memberId);

    // Order + OrderItem + Product 한 번에 가져옴 (GET용)
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH oi.product WHERE o.orderId = :orderId AND o.member.memberId = :memberId")
    Optional<Order> findByOrderIdAndMemberIdWithItems(@Param("orderId") Long orderId, @Param("memberId") Long memberId);

    // 상태 변경용 — 동시 취소 요청의 중복 재고 복구 방지
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH oi.product WHERE o.orderId = :orderId AND o.member.memberId = :memberId")
    Optional<Order> findByOrderIdAndMemberIdWithItemsForUpdate(@Param("orderId") Long orderId, @Param("memberId") Long memberId);
}