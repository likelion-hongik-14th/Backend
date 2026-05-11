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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 유저 가입시 빈 장바구니를 생성해주는 공식 창구
    public static Cart createEmptyCart(User user){
        Cart cart = new Cart();
        cart.user = user;
        return cart;
    }

    // 도메인 비즈니스 로직: 장바구니에 상품 담기
    public void addProduct(Product product, Integer quantity){

        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.addQuantity(quantity);
                return;
            }
        }

        CartItem cartItem = CartItem.builder()
                .cart(this)
                .product(product)
                .quantity(quantity)
                .build();
        this.cartItems.add(cartItem);
    }

    // 장바구니 총액 계산
    public int calculateTotalPrice(){
        return cartItems.stream()
                .mapToInt(CartItem::calculatePrice)
                .sum();
    }

    // 장바구니 비우기 (결제 완료 후 호출)
    public void clearCart(){
        this.cartItems.clear();
    }
}
