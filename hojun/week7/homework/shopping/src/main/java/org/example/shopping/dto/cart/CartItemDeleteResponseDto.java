package org.example.shopping.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItemDeleteResponseDto {
    private String productName;
    private Integer quantity;
}
