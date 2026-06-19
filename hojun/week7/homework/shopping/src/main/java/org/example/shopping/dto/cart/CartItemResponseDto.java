package org.example.shopping.dto.cart;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponseDto {
    private Long productId;
    private Long itemId;
    private String productName;
    private String description;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
}
