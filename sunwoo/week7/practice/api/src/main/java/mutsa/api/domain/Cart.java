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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) //조회하기 위한 연결
    //mappedBy: 관계의 주인 설정(외래키가 있는 쪽)
    @Builder.Default
    private List<CartItem> cartItems = new ArrayList<>();

    public static Cart createCart(Users users) {
        Cart cart = new Cart();
        cart.users = users;
        return cart;
    }

    public void addProduct(Product product, int quantity) {
        CartItem cartItem = CartItem.create(this, product, quantity);
        this.cartItems.add(cartItem);
    }

    public int calculateTotalPrice() {
        return cartItems.stream()
                .mapToInt(CartItem::calculatePrice)
                .sum();
    }
}
