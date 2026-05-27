package com.app.mutsa_shoppingmall.dto;

import com.app.mutsa_shoppingmall.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ProductDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private List<ProductDetail> products;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProductDetail {
        private Long id;
        private String name;
        private int price;
        private int stock;

        public static ProductDetail from(Product p) {
            return ProductDetail.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .price(p.getPrice())
                    .stock(p.getStock())
                    .build();
        }
    }
}