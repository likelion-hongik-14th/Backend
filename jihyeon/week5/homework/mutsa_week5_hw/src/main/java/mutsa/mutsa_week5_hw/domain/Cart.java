package mutsa.mutsa_week5_hw.domain;

import jakarta.persistence.*;
import lombok.*;

import mutsa.mutsa_week5_hw.domain.Member;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 수정: id 값을 DB가 자동 생성하고, 새 cart 마다 1씩 증가시킴을 의미
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public static Cart createCart(Member member) { //수정: member를 넣은 상태로 cart 생성
        Cart cart = new Cart();
        cart.member = member;
        return cart;
    }

    public CartItem addProduct(Product product, int quantity) { // 수정: 장바구니에 같은 상품을 두 번 담았을 때 cartitem이 두개 생기는 문제 해결

        // 이미 장바구니에 있는 상품인지 확인
        for (CartItem item : cartItems) {
            // 이미 있는 상품이면 수량만 증가
            if (item.getProduct().getId().equals(product.getId())) {
                item.increaseQuantity(quantity);
                return item;
            }
        }
        // 없는 상품이면 새 cartitem 생성
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
