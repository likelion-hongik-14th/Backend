package com.study.shop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "orders_id")
    private Order order;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int orderPrice;

    @Column(nullable = false)
    private String productName;

    public OrderItem(Product product, Order order, int quantity, int orderPrice, String productName) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.productName = productName;
    }

    public int getItemTotalPrice() {
        return orderPrice * quantity;
    }

}
