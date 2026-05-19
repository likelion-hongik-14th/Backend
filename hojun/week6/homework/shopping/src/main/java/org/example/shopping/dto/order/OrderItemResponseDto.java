package org.example.shopping.dto.order;

import lombok.Getter;
import org.example.shopping.domain.OrderItem;

@Getter
public class OrderItemResponseDto {
    private String productName;
    private Integer quantity;
    private Integer totalPrice;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.productName = orderItem.getProduct().getName();
        this.quantity = orderItem.getQuantity();
        this.totalPrice = orderItem.getTotalPrice();
    }
}
