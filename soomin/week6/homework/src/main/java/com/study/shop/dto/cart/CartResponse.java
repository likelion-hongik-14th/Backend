package com.study.shop.dto.cart;

import lombok.Getter;

import java.util.List;

@Getter
public class CartResponse {

    private Long cartId;
    private Long memberId;
    private List<CartItemResponse> items;
    private int totalPrice;

    public CartResponse(Long cartId, Long memberId, List<CartItemResponse> items, int totalPrice) {
        this.cartId = cartId;
        this.memberId = memberId;
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
