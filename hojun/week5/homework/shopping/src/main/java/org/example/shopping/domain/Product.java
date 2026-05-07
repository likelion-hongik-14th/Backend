package org.example.shopping.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private Integer stock;

    public void removeStock(int quantity){
        int restStock = this.stock - quantity;
        if(restStock < 0){
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock = restStock;
    }

    public void addStock(int quantity){
        this.stock += quantity;
    }
}
