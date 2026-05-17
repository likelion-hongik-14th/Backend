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
import mutsa.week2.product.Product;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "장바구니아이템")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "장바구니아이템아이디")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "상품아이디", nullable = false)
    private Product product;

    @Column(name = "수량", nullable = false)
    private Integer quantity;

    @Column(name = "이미지URL", length = 300)
    private String imageUrl;

    @Builder
    public CartItem(Product product, Integer quantity, String imageUrl) {
        this.product = product;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
