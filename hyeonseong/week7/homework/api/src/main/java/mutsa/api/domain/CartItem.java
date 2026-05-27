package mutsa.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Builder
    public CartItem(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    // 수량 변경 (사용자가 수량을 직접 숫자로 입력하여 덮어씌울 때)
    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // 해당 품목의 총 가격 계산
    public int calculatePrice(){
        return this.product.getPrice() * this.quantity;
    }

    // 수량 누적 (기존에 있던 상푸믈 또 담았을 때 기존 수량에 더하기)
    public void addQuantity(int quantity){
        this.quantity += quantity;
    }
}