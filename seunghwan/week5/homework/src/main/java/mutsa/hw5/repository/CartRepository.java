package mutsa.hw5.repository;

import mutsa.hw5.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product WHERE c.member.memberId = :memberId")
    Optional<Cart> findByMemberIdWithItems(@Param("memberId") Long memberId);

    Optional<Cart> findByMember_MemberId(Long memberId);
}
