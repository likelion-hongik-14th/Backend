package mutsa.session5.Repository;

import mutsa.session5.Entity.Cart;
import mutsa.session5.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMember_MemberId(Long memberId);
}
