package mutsa.homework.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder(access = AccessLevel.PRIVATE)
    private CartItem(Cart cart, Product product, int quantity){
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public static CartItem create(
            Cart cart,
            Product product,
            int quantity
    ){
        CartItem newCartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();

        cart.addCartItem(newCartItem);

        return newCartItem;
    }

    public void validateUser(Long userId){
        if(!this.cart.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("잘못된 접근입니다. (권한 없음)");
        }
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void updateQuantity(int quantity) { this.quantity = quantity; }

    public int calculatePrice(){
        return this.product.getPrice() * this.quantity;
    }
}
