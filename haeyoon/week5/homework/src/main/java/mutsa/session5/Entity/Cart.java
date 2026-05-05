package mutsa.session5.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue
    private Long cart_id;

    @Column(name = "user_id")
    private Long userId;

    @Builder.Default
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    public CartItem addProduct(Product product, int quantity) {
        CartItem cartItem = new CartItem(this, product, quantity);
        this.cartItems.add(cartItem);
        return cartItem;
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
