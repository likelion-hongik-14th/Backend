package mutsa.api.dto;

import lombok.Getter;
import mutsa.api.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponseDto {
    private Long orderId;
    private String orderStatus;
    private LocalDateTime orderDate;
    private List<OrderItemResponseDto> orderItems;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getStatus().name();
        this.orderDate = order.getOrderDate();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemResponseDto::new)
                .toList();
    }
}
