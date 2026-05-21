package org.example.shopping.dto.cart;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CartResponseDto {
    private List<CartItemResponseDto> items;
    private Long totalOrderPrice;
}
