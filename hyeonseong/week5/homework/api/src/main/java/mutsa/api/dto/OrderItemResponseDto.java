package mutsa.api.dto;

import lombok.Getter;
import mutsa.api.domain.OrderItem;

@Getter
public class OrderItemResponseDto {
    private String productName;
    private int orderPrice;
    private int count;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.productName = orderItem.getProduct().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
