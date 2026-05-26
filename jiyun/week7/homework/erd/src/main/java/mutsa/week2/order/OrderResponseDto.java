package mutsa.week2.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        Long memberId,
        OrderStatus status,
        LocalDateTime orderedAt,
        String shippingName,
        String shippingZipCode,
        String shippingAddress,
        String shippingPhone,
        List<OrderItemResponseDto> items,
        BigDecimal totalPrice
) {
    public static OrderResponseDto from(Order order) {
        List<OrderItemResponseDto> items = order.getItems().stream()
                .map(OrderItemResponseDto::from)
                .toList();
        return new OrderResponseDto(
                order.getId(),
                order.getMember().getId(),
                order.getStatus(),
                order.getOrderedAt(),
                order.getShippingName(),
                order.getShippingZipCode(),
                order.getShippingAddress(),
                order.getShippingPhone(),
                items,
                order.getTotalPrice()
        );
    }
}
