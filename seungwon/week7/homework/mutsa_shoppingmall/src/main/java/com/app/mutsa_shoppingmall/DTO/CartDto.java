package com.app.mutsa_shoppingmall.DTO;

import com.app.mutsa_shoppingmall.Entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CartDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CartResponse {
        private List<CartItemDetail> cartItems;
        private int totalPrice;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CartItemDetail {
        private Long id;
        private String name;
        private int price;
        private int quantity;
        private int stock;

        public static CartItemDetail from(CartItem item) {
            return CartItemDetail.builder()
                    .id(item.getId())
                    .name(item.getProduct().getName())
                    .price(item.getProduct().getPrice())
                    .quantity(item.getQuantity())
                    .stock(item.getProduct().getStock())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemRequest {
        private Long productId;
        private int quantity;
        private String color;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ItemResponse {
        private Long id;
        private Long productId;
        private int quantity;
        private String color;
    }
}