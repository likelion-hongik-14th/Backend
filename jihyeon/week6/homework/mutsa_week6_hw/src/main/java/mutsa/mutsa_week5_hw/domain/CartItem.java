package mutsa.mutsa_week5_hw.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 수정: id 값을 DB가 자동 생성하고, 새 cartitem 마다 1씩 증가시킴을 의미
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false) // 수정: cart_id는 비어있으면 안됨
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false) // 수정: product_id도 비어있으면 안됨
    private Product product;

    @Column(nullable = false)
    private int quantity;

    public static CartItem create(Cart cart, Product product, int quantity) {
        return CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();
    }

    public void increaseQuantity(int quantity) {
        if (quantity <= 0) { // 수정: 음수 방어 로직 추가
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
        this.quantity += quantity;
    }

    public int calculatePrice() {
        return product.getPrice() * quantity;
    }
}
