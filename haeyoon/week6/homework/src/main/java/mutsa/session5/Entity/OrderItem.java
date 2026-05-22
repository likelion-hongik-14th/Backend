package mutsa.session5.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long orderItemId;

    private String name;
    private int orderQuantity;
    private Long orderPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItem(Order order, Product product, int orderQuantity, Long orderPrice) {
        this.order = order;
        this.product = product;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getTotalPrice() {
        return getOrderPrice() * getOrderQuantity();
    }

    public static OrderItem createOrderItem(Product product, Long orderPrice, int count) {
        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .orderPrice(orderPrice)
                .orderQuantity(count)
                .build();

        product.removeStock(count);
        return orderItem;
    }
}