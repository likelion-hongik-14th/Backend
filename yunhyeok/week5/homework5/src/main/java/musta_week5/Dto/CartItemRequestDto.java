package musta_week5.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemRequestDto {
    private String productId;
    private Integer quantity;
    private String optionId;
}