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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private int stock;
    private String description;

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new GeneralException(ErrorCode.STOCK_NOT_ENOUGH);
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        this.stock += quantity;
    }
}