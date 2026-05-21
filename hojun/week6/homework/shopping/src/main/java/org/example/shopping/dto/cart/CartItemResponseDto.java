package org.example.shopping.dto.cart;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponseDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
}
