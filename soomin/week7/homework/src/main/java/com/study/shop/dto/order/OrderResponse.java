package com.study.shop.dto.order;

import com.study.shop.domain.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {

    private Long orderId;
    private Long memberId;

    private String addressName;
    private String zipCode;
    private String address;
    private String detailAddress;
    private String phoneNumber;

    private String status;
    private int totalPrice;
    private LocalDateTime orderedAt;
    private List<OrderItemResponse> items;

    public OrderResponse(Order order, List<OrderItemResponse> items) {
        this.orderId = order.getId();
        this.memberId = order.getMember().getId();

        this.addressName = order.getAddressName();
        this.zipCode = order.getZipCode();
        this.address = order.getAddress();
        this.detailAddress = order.getDetailAddress();
        this.phoneNumber = order.getPhoneNumber();

        this.status = order.getStatus().name();
        this.totalPrice = order.getTotalPrice();
        this.orderedAt = order.getOrderedAt();
        this.items = items;
    }

}
