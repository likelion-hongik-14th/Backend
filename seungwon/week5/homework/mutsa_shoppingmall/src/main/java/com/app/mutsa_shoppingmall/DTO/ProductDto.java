package com.app.mutsa_shoppingmall.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class ProductDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private List<ProductDetail> products;
    }

    @Getter
    @AllArgsConstructor
    public static class ProductDetail {
        private Long id;
        private String name;
        private int price;
        private int stock;
    }
}
