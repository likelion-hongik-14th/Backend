package mutsa.mutsa_week5_hw.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public static Cart createCart() {
        return new Cart();
    }

    public CartItem addProduct(Product product, int quantity) {
        CartItem cartItem = CartItem.create(this, product, quantity);
        this.cartItems.add(cartItem);
        return cartItem;
    }

    public int calculateTotalPrice() {
        return cartItems.stream()
                .mapToInt(CartItem::calculatePrice)
                .sum();
    }
}
