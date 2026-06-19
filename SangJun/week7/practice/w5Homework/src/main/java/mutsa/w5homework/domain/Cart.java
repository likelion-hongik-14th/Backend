package mutsa.w5homework.domain;

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

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart(Member member) {

        this.member = member;
    }

    protected void connectMember(Member member) {
        this.member = member;
    }

    public Long getTotalPrice() {
        return cartItems.stream()
                .mapToLong(cartItem -> cartItem
                        .getProduct().getPrice() * cartItem.getCount())
                .sum();
    }
    public Long getTotalQuantity() {
        return cartItems.stream().mapToLong(CartItem::getCount).sum();
    }
    public void clearCart(){
        this.cartItems.clear();
    }

}
