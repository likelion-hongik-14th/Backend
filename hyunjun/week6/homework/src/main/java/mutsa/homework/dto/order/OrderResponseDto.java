package mutsa.homework.dto.order;

import mutsa.homework.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long orderId,
        List<OrderItemResponseDto> items,
        int totalPrice,
        String orderStatus,
        String orderStatusDescription,
        LocalDateTime createdAt

) {
    public static OrderResponseDto from(Order order){
        List<OrderItemResponseDto> items = order.getOrderItems().stream()
                .map(OrderItemResponseDto::from)
                .toList();

        return new OrderResponseDto(
                order.getId(),
                items,
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getStatus().getDescription(),
                order.getCreatedAt()
        );
    }
}
