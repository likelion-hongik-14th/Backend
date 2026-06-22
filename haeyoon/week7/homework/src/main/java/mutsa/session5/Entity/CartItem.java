package mutsa.session5.Entity;

import jakarta.persistence.*;
import lombok.*;
import mutsa.session5.global.apipayload.exception.CartException;
import mutsa.session5.global.apipayload.exception.code.CartErrorCode;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CartItem {
    @Id @GeneratedValue
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    public CartItem(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void updateQuantity(int quantity) {
        if (quantity < 1) {
            throw new CartException(CartErrorCode.INVALID_QUANTITY);
        }
        this.quantity = quantity;
    }

    public Long calculatePrice() {
        return this.product.getPrice() * this.quantity;
    }
}
