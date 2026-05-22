package com.study.shop.dto.order;

import com.study.shop.domain.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemResponse {

    private Long orderItemId;
    private Long productId;
    private String productName;
    private int orderPrice;
    private int quantity;
    private int itemTotalPrice;

    public OrderItemResponse(OrderItem orderItem) {
        this.orderItemId = orderItem.getId();
        this.productId = orderItem.getProduct().getId();
        this.productName = orderItem.getProductName();
        this.orderPrice = orderItem.getOrderPrice();
        this.quantity = orderItem.getQuantity();
        this.itemTotalPrice = orderItem.getItemTotalPrice();
    }

}
