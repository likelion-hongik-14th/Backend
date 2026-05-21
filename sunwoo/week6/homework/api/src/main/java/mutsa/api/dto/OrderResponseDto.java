package mutsa.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.api.domain.Orders;
import mutsa.api.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private Long addressId;
    private OrderStatus orderStatus;
    private Long totalPrice;
    private LocalDateTime date;
    private List<OrderItemResponseDto> orderItems;

    public static OrderResponseDto from(Orders orders) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.orderId = orders.getId();
        responseDto.userId = orders.getUsers().getId();
        responseDto.addressId = orders.getAddress().getId();
        responseDto.orderStatus = orders.getStatus();
        responseDto.totalPrice = orders.getTotal_price();
        responseDto.date = orders.getDate();
        responseDto.orderItems = orders.getOrderItems().stream()
                .map(OrderItemResponseDto::from)
                .toList();
        return responseDto;
    }
}
