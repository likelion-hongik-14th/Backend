package com.app.mutsa_shoppingmall.Entity;

import com.app.mutsa_shoppingmall.exception.ErrorCode;
import com.app.mutsa_shoppingmall.exception.GeneralException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public void update(int quantity, String color) {
        if (quantity <= 0) {
            throw new GeneralException(ErrorCode.INVALID_QUANTITY);
        }
        this.quantity = quantity;
        this.color = color;
    }
}