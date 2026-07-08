package com.study.shop.dto.product;

import com.study.shop.domain.Product;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductListResponse {

    private Long productId;
    private String name;
    private int price;
    private int stockQuantity;
    private String status;

    public ProductListResponse(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.status = product.getStatus().name();
    }
}
