package com.study.shop.dto.cart;

import com.study.shop.domain.CartItem;
import lombok.Getter;

@Getter
public class CartItemResponse {

    private Long cartItemId;
    private Long productId;
    private String productName;
    private int price;
    private int quantity;
    private int itemTotalPrice;

    public CartItemResponse(CartItem cartItem) {
        this.cartItemId = cartItem.getId();
        this.productId = cartItem.getProduct().getId();
        this.productName = cartItem.getProduct().getName();
        this.price = cartItem.getProduct().getPrice();
        this.quantity = cartItem.getQuantity();
        this.itemTotalPrice = cartItem.getItemTotalPrice();
    }
}
