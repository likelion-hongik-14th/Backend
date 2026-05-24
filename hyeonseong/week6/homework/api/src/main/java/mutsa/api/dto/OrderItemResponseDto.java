package mutsa.api.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.api.domain.OrderItem;

@Getter
@Builder
public class OrderItemResponseDto {
    private String productName;
    private int orderPrice;
    private int count;

    public static OrderItemResponseDto of(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .productName(orderItem.getProductName())
                .orderPrice(orderItem.getOrderPrice())
                .count(orderItem.getCount())
                .build();
    }
}
