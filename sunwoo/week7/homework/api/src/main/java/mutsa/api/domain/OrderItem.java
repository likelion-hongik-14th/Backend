package mutsa.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String product_name;
    private int order_price;
    private int quantity;

    public static OrderItem create(Product product, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        return OrderItem.builder()
                .product(product)
                .product_name(product.getName())
                .order_price(product.getPrice())
                .quantity(quantity)
                .build();
    }

    void setOrder(Orders orders) {
        this.orders = orders;
    }

    public Long calculateTotalPrice() {
        return (long) order_price * quantity;
    }

    public void restoreStock() {
        product.restoreStock(quantity);
    }
}
