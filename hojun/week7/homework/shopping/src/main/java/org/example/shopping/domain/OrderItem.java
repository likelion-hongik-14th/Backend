package org.example.shopping.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String productName;
    private String description;
    private Integer unitPrice;
    private Integer quantity;

    public static OrderItem createOrderItem(Order order, Product product, Integer quantity){

        product.removeStock(quantity);

        return OrderItem.builder()
                .order(order)
                .product(product)
                .productName(product.getName())
                .description(product.getDescription())
                .unitPrice(product.getPrice())
                .quantity(quantity)
                .build();
    }

    public void cancel(){
        getProduct().addStock(this.quantity);
    }
    public Integer getTotalPrice() {
        return this.unitPrice * this.quantity;
    }
}
