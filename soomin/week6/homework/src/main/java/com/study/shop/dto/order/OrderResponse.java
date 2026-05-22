package com.study.shop.dto.order;

import com.study.shop.domain.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {

    private Long orderId;
    private Long memberId;
    private Long addressId;
    private String status;
    private int totalPrice;
    private LocalDateTime orderedAt;
    private List<OrderItemResponse> items;

    public OrderResponse(Order order, List<OrderItemResponse> items) {
        this.orderId = order.getId();
        this.memberId = order.getMember().getId();
        this.addressId = order.getAddress().getId();
        this.status = order.getStatus().name();
        this.totalPrice = order.getTotalPrice();
        this.orderedAt = order.getOrderedAt();
        this.items = items;
    }

}
