package mutsa.week2.cart;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.week2.global.apiPayload.exception.BusinessException;
import mutsa.week2.product.Product;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "image_url", length = 300)
    private String imageUrl;

    @Builder
    public CartItem(Cart cart, Product product, Integer quantity, String imageUrl) {
        validateQuantity(quantity);
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    void assignCart(Cart cart) {
        this.cart = cart;
    }

    public void updateQuantity(Integer quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity < 1) {
            throw new BusinessException(CartErrorCode.INVALID_QUANTITY);
        }
    }
}
