package org.example.shopping.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.shopping.dto.product.ProductRequestDto;
import org.example.shopping.global.apiPayload.code.domain.ProductErrorCode;
import org.example.shopping.global.apiPayload.exception.ProjectException;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;

    public void removeStock(Integer quantity){
        int restStock = this.stock - quantity;
        if(restStock < 0){
            throw new ProjectException(ProductErrorCode.OUT_OF_STOCK);
        }
        this.stock = restStock;
    }

    public void addStock(Integer quantity){
        this.stock += quantity;
    }

    public void updateInfo(ProductRequestDto request){
        this.name = request.getName();
        this.description = request.getDescription();
        this.price = request.getPrice();
        this.stock = request.getStock();
    }
}
