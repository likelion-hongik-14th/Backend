package mutsa.homework.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int orderPrice;
    private int quantity;

    @Builder(access = AccessLevel.PRIVATE)
    private OrderItem(
            Order order,
            Product product,
            int orderPrice,
            int quantity
    ) {
        this.order = order;
        this.product = product;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }

    public static OrderItem create(
            Order order,
            Product product,
            int orderPrice,
            int quantity
    ) {
        product.decreaseStock(quantity);

        OrderItem newOrderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .orderPrice(orderPrice)
                .quantity(quantity)
                .build();

        order.addOrderItem(newOrderItem);

        return newOrderItem;
    }

    public int calculatePrice(){ return this.orderPrice * this.quantity; }

    public void cancelOrderItem() {
        this.getProduct().increaseStock(this.quantity);
    }
}
