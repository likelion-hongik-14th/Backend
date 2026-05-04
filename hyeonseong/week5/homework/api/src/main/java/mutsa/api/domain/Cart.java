package mutsa.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public void addProduct(Product product, Integer quantity){
        CartItem cartItem = CartItem.builder()
                .cart(this)
                .product(product)
                .quantity(quantity)
                .build();
        this.cartItems.add(cartItem);
    }

    public int calculateTotalPrice(){
        return cartItems.stream()
                .mapToInt(CartItem::calculatePrice)
                .sum();
    }

    public static Cart createEmptyCart(){
        return new Cart();
    }

}
