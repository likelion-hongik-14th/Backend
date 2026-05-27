package mutsa.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    public static CartItem create(Cart cart, Product product, int quantity) {
        validateQuantity(quantity);
        return CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();
    }

    public void increaseQuantity(int quantity) {
        validateQuantity(quantity);
        this.quantity += quantity;
    }

    public void updateQuantity(int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public int calculatePrice() {
        return product.getPrice() * quantity;
    }

    private static void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
    }
}
