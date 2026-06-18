package mutsa.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.api.domain.OrderItem;

@NoArgsConstructor
@Getter
public class OrderItemResponseDto {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private int orderPrice;
    private int quantity;

    public static OrderItemResponseDto from(OrderItem orderItem) {
        OrderItemResponseDto responseDto = new OrderItemResponseDto();
        responseDto.orderItemId = orderItem.getId();
        responseDto.productId = orderItem.getProduct().getId();
        responseDto.productName = orderItem.getProduct_name();
        responseDto.orderPrice = orderItem.getOrder_price();
        responseDto.quantity = orderItem.getQuantity();
        return responseDto;
    }
}
