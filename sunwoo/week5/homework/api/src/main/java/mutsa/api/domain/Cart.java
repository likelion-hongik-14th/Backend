package mutsa.api.domain;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) //조회하기 위한 연결
    //mappedBy: 관계의 주인 설정(외래키가 있는 쪽)
    private List<CartItem> cartItems = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        CartItem cartItem = CartItem.builder()
                .cart(this)
                .product(product)
                .quantity(quantity)
                .build();
        this.cartItems.add(cartItem);
    }

    public int calculateTotalPrice() {
        return cartItems.stream()
                .mapToInt(CartItem::calculatePrice)
                .sum();
    }

    public static Cart createCart() {
        return new Cart();
    }
}
