package com.study.shop.dto.product;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.study.shop.domain.Product;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"productId", "categoryId", "categoryName", "name", "price", "stockQuantity", "description",
        "status", "createdAt", "updatedAt"})
public class ProductResponse {

    private Long productId;
    private Long categoryId;
    private String categoryName;
    private String name;
    private int price;
    private int stockQuantity;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponse(Product product) {
        this.productId = product.getId();
        this.categoryId = product.getCategory().getId();
        this.categoryName = product.getCategory().getName();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.description = product.getDescription();
        this.status = product.getStatus().name();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}
