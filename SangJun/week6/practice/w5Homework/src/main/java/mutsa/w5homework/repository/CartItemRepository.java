package mutsa.w5homework.repository;

import mutsa.w5homework.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    /* null값 반환 대비해서 Optional로 선언
    기존 상품 수량 업데이트를 위한 조회 메서드
     */
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
}
