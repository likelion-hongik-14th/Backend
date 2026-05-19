package org.example.shopping.dto.cart;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemUpdateDto {
    private int quantity;
}
