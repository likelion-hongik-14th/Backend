package musta_week5.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemDto {
    private Long productId;
    private Integer quantity;
    private String optionId;
}
