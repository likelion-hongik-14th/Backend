package org.example.shopping.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.shopping.dto.product.ProductRequestDto;

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
    private Integer price;
    private Integer stock;

    public void removeStock(Integer quantity){
        Integer restStock = this.stock - quantity;
        if(restStock < 0){
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock = restStock;
    }

    public void addStock(Integer quantity){
        this.stock += quantity;
    }

    public void updateInfo(ProductRequestDto request){
        this.name = request.getName();
        this.price = request.getPrice();
        this.stock = request.getStock();
    }
}
