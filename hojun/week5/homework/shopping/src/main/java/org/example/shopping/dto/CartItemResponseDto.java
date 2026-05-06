package org.example.shopping.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponseDto {
    private Long productId;
    private String productName;
    private int quantity;
    private Long unitPrice;
    private Long totalPrice;
}
