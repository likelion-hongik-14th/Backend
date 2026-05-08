package musta_week5.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor

public class OrderResponseDto {
    private Long orderId;
    private String status;
    private String address;
    private Integer totalPrice;
}
