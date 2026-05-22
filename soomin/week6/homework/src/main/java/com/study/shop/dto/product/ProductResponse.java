package com.study.shop.dto.product;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.study.shop.domain.Product;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"productId", "name", "price", "stockQuantity", "description",
        "status", "createdAt", "updatedAt"})
public class ProductResponse {

    private Long productId;
    private String name;
    private int price;
    private int stockQuantity;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponse(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.description = product.getDescription();
        this.status = product.getStatus().name();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}
