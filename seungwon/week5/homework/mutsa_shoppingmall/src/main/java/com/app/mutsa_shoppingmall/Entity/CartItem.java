package com.app.mutsa_shoppingmall.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;
    private String color; // API 명세서 처리를 위해 추가된 필드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public CartItem(Cart cart, Product product, Long quantity, String color) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.color = color;
    }

    public void update(Long quantity, String color) {
        this.quantity = quantity;
        this.color = color;
    }
}
