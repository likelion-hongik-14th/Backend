package com.study.shop.dto.order;

import com.study.shop.domain.Order;
import lombok.Getter;

@Getter
public class OrderStatusResponse {

    private Long orderId;
    private String status;
    private String message;

    public OrderStatusResponse(Order order, String message) {
        this.orderId = order.getId();
        this.status = order.getStatus().name();
        this.message = message;
    }

}
