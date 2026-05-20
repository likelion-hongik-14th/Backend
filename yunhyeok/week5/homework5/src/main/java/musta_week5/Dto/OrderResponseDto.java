package musta_week5.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import musta_week5.domain.DeliverStatus;
import musta_week5.domain.OrderStatus;


@Getter
@AllArgsConstructor

public class OrderResponseDto {
    private Long id;
    private OrderStatus status;
    private Integer totalPrice;
    private DeliverStatus deliverStatus;
}
