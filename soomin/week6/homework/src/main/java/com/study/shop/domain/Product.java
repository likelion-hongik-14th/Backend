package com.study.shop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Product(String name, int price, int stockQuantity, String description, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String name, int price, int stockQuantity, String description, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.status = ProductStatus.DELETE;
        this.updatedAt = LocalDateTime.now();
    }

    public void decreaseStock(int quantity) {
        if (this.stockQuantity < quantity) {
            throw new IllegalArgumentException("상품 재고가 부족합니다.");
        }

        this.stockQuantity -= quantity;

        if (this.stockQuantity == 0) {
            this.status = ProductStatus.SOLD_OUT;
        }

        this.updatedAt = LocalDateTime.now();
    }

    public void increaseStock(int quantity) {
        this.stockQuantity += quantity;

        if (this.stockQuantity>0 && this.status==ProductStatus.SOLD_OUT) {
            this.status = ProductStatus.SELLING;
        }

        this.updatedAt = LocalDateTime.now();
    }

}
