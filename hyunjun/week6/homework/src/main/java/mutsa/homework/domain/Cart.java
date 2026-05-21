package mutsa.homework.domain;

import jakarta.persistence.*;
import lombok.*;
import mutsa.homework.global.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Cart(User user){
        this.user = user;
    }

    public static Cart create(User user) {
        return Cart.builder()
                .user(user)
                .build();
    }

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    public int getTotalPrice(){
        return cartItems.stream()
                .mapToInt(CartItem::calculatePrice)
                .sum();
    }
}
