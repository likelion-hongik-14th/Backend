package mutsa.session5.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import mutsa.session5.Entity.Order;
import mutsa.session5.Entity.OrderItem;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class OrderItemResponseDto {
    private Long productId;

    private Long orderId;
    private String name;
    private Long orderPrice;
    private int orderQuantity;
    private LocalDateTime orderDate;
    private Long totalPrice;

    public static OrderItemResponseDto of(Order order, OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .orderId(order.getOrderId())
                .productId(orderItem.getProduct().getProductId())
                .name(orderItem.getName())
                .orderPrice(orderItem.getOrderPrice())
                .orderQuantity(orderItem.getOrderQuantity())
                .orderDate(order.getOrderDate())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}
