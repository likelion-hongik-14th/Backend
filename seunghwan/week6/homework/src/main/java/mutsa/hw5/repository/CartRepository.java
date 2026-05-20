package mutsa.hw5.repository;

import mutsa.hw5.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product WHERE c.member.memberId = :memberId")
    Optional<Cart> findByMemberIdWithItems(@Param("memberId") Long memberId);
    // Query를 엔터티 기준으로 작성한 JPQL
    // 매서드 이름 방식은 단순조회만 가능, JOIN FETCH가 필요한 경우에만 직접 JPQL을 씀

    Optional<Cart> findByMember_MemberId(Long memberId);
}
