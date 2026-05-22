package mutsa.api.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.api.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponseDto {
    private Long orderId;
    private String orderStatus;
    private LocalDateTime orderDate;
    private List<OrderItemResponseDto> orderItems;

    private String addressName;
    private String fullAddress;

    public static OrderResponseDto of(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .orderStatus(order.getStatus().name())
                .orderDate(order.getOrderDate())
                .addressName(order.getAddress().getAddressName())
                .fullAddress(order.getAddress().getAddress())
                .orderItems(order.getOrderItems().stream()
                        .map(OrderItemResponseDto::of)
                        .toList())
                .build();
    }
}
