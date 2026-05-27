package org.example.shopping.dto.order;

import lombok.Getter;
import org.example.shopping.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponseDto {

    private Long orderId;
    private LocalDateTime orderDate;
    private String address;
    private String addressAlias;
    private String phone;
    private String zipcode;
    private Integer finalOrderPrice;
    private List<OrderItemResponseDto> orderItems;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate();
        this.address = order.getAddress();
        this.addressAlias = order.getName();
        this.phone = order.getPhone();
        this.zipcode = order.getZipcode();
        this.finalOrderPrice = order.getTotalPrice();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemResponseDto::new)
                .toList();
    }
}
