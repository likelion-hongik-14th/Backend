package com.app.mutsa_shoppingmall.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class CartDto {
    // GET /cart 응답용
    @Getter
    @AllArgsConstructor
    public static class CartResponse {
        private List<CartItemDetail> cartItem; // API 명세서 배열 키 이름 일치
        private int totalPrice;
    }

    @Getter
    @AllArgsConstructor
    public static class CartItemDetail {
        private Long id;
        private String name;
        private int price;
        private Long quantity;
        private int stock; // 실제 상품의 남은 재고
    }

    // POST, PATCH 요청용
    @Getter
    public static class ItemRequest {
        private Long productId;
        private Long quantity;
        private String color;
    }

    // POST, PATCH 응답용
    @Getter
    @AllArgsConstructor
    public static class ItemResponse {
        private Long id; // CartItem의 ID
        private Long productId;
        private Long quantity;
        private String color;
    }
}

