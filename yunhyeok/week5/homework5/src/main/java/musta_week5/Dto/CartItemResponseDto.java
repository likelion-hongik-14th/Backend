package musta_week5.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItemResponseDto {
    private Long Id;
    private Long optionId;
    private Integer quantity;
}
