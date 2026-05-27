package mutsa.shop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public void updateQuantity(Long quantity) {
        // 데이터 보호: 수량이 0이나 음수가 되는 것을 방지
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("장바구니 수량은 1개 이상이어야 합니다.");
        }
        this.quantity = quantity;
    }

}
