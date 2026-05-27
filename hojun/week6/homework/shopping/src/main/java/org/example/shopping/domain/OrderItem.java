package org.example.shopping.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer unitPrice;
    private Integer quantity;

    public static OrderItem createOrderItem(Order order, Product product, Integer quantity){
        product.removeStock(quantity);

        return OrderItem.builder()
                .order(order)
                .product(product)
                .productName(product.getName())
                .unitPrice(product.getPrice())
                .quantity(quantity)
                .build();
    }

    public void cancle(){
        getProduct().addStock(this.quantity);
    }
    public Integer getTotalPrice() {
        return this.unitPrice * this.quantity;
    }
}
