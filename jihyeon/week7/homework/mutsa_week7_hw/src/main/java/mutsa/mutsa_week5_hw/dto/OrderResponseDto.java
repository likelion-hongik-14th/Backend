package mutsa.mutsa_week5_hw.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.mutsa_week5_hw.domain.Order;
import mutsa.mutsa_week5_hw.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

// 주문 조회(응답) -> 주문 기본 정보(주문 ID, 일시, 상태)와 주문 상품 상세 리스트를 결합하여 클라이언트에게 반환
@Getter
@Builder
public class OrderResponseDto {

    private Long orderId;

    private OrderStatus status;

    private LocalDateTime orderedAt;

    private String deliveryName;
    private String deliveryZipcode;
    private String deliveryAddress;
    private String deliveryPhone;

    private List<OrderItemResponseDto> orderItems;

    public static OrderResponseDto from(Order order) {

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .orderedAt(order.getOrderedAt())
                .deliveryName(order.getDeliveryName())
                .deliveryZipcode(order.getDeliveryZipcode())
                .deliveryAddress(order.getDeliveryAddress())
                .deliveryPhone(order.getDeliveryPhone())
                .orderItems(
                        order.getOrderItems()
                                .stream()
                                .map(OrderItemResponseDto::from)
                                .toList()
                )
                .build();
    }

}
