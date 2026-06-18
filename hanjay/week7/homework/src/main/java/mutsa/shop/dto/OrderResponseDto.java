package mutsa.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.shop.domain.Order;
import mutsa.shop.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long orderId;
    private Long memberId;
    private Long addressId;
    private OrderStatus status; // 만약 내부에 선언하셨다면 Orders.OrderStatus 로 변경
    private LocalDateTime orderDate;
    private List<OrderItemResponseDto> orderItems; // 주문한 상품 리스트

    // 정적 팩토리 메서드: 엔티티를 DTO로 안전하게 변환
    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .memberId(order.getMember().getId())
                .addressId(order.getAddress().getId())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .orderItems(order.getOrderItems().stream()
                        .map(OrderItemResponseDto::from)
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * 내부 클래스: 주문 상품 상세 정보 응답 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponseDto {
        private Long orderItemId;
        private Long productId;
        private String name;        // ★ 주문 시점의 상품명 (스냅샷)
        private Long orderPrice;    // ★ 주문 시점의 가격 (스냅샷)
        private Long quantity;

        public static OrderItemResponseDto from(mutsa.shop.domain.OrderItem orderItem) {
            return OrderItemResponseDto.builder()
                    .orderItemId(orderItem.getId())
                    .productId(orderItem.getProduct().getId())
                    .name(orderItem.getName())
                    .orderPrice(orderItem.getOrderPrice())
                    .quantity(orderItem.getQuantity())
                    .build();
        }
    }
}
