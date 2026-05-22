package com.app.mutsa_shoppingmall.DTO;

import com.app.mutsa_shoppingmall.Entity.Order;
import com.app.mutsa_shoppingmall.Entity.OrderItem;
import com.app.mutsa_shoppingmall.Entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        private Long productId;
        private int quantity;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private OrderStatus status;
        private LocalDateTime orderedAt;
        private List<OrderItemDetail> orderItems;

        public static Response from(Order order) {
            return Response.builder()
                    .id(order.getId())
                    .status(order.getStatus())
                    .orderedAt(order.getOrderedAt())
                    .orderItems(order.getOrderItems().stream()
                            .map(OrderItemDetail::from)
                            .toList())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class OrderItemDetail {
        private Long id;
        private String productName;
        private int price;
        private int quantity;

        public static OrderItemDetail from(OrderItem item) {
            return OrderItemDetail.builder()
                    .id(item.getId())
                    .productName(item.getProductName())
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .build();
        }
    }
}