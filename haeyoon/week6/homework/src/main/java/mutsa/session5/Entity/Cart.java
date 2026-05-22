package mutsa.session5.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    public CartItem addProduct(Product product, int quantity) {
        Optional<CartItem> existingItem = this.cartItems.stream()
                .filter(item -> item.getProduct().getProductId().equals(product.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.increaseQuantity(quantity);
            return item;
        } else {
            // 3. 없다면 새로 생성해서 리스트에 넣고 반환
            CartItem cartItem = CartItem.builder()
                    .cart(this)
                    .product(product)
                    .quantity(quantity)
                    .build();
            this.cartItems.add(cartItem);
            return cartItem;
        }
    }

    public Long calculateTotalPrice() {
        if (cartItems == null || cartItems.isEmpty()) {
            return 0L;
        }
        return cartItems.stream()
                .mapToLong(CartItem::calculatePrice)
                .sum();
    }
}
