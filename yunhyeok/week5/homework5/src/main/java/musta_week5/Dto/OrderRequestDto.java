package musta_week5.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequestDto {

    private String userId;
    private List<OrderItemDto> items;
    private Long addressId;
    private Integer totalPrice;

}
