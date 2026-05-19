package org.example.shopping.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DirectOrderRequestDto {
    private Long productId;
    private Long addressId;
    private Integer quantity;
}
