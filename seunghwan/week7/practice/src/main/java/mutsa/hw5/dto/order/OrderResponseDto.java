package mutsa.hw5.dto.order;

import lombok.Builder;
import lombok.Getter;
import mutsa.hw5.domain.Order;
import mutsa.hw5.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class OrderResponseDto {

    private Long orderId;
    private OrderStatus status;
    private String receiverName;
    private String postalCode;
    private String address;
    private String phoneNumber;
    private List<OrderItemResponseDto> items;
    private Long totalPrice;
    private LocalDateTime orderedAt;

    // Entity → DTO 변환
    // Service에서 호출하지만, 변환 로직을 DTO에 숨겨두어서 깔끔하게 유지
    public static OrderResponseDto from(Order order) {
        List<OrderItemResponseDto> items = order.getOrderItems().stream()
                .map(OrderItemResponseDto::from)
                .collect(Collectors.toList());

        Long totalPrice = order.getOrderItems().stream()
                .mapToLong(item -> item.getProductPrice() * item.getItemQuantity())
                .sum();

        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .status(order.getOrderStatus())
                .receiverName(order.getReceiverName())
                .postalCode(order.getPostalCode())
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .items(items)
                .totalPrice(totalPrice)
                .orderedAt(order.getCreatedAt())
                .build();
    }
}
