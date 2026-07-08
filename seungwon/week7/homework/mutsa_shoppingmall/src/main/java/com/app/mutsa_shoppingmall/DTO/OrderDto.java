package com.app.mutsa_shoppingmall.DTO;

import com.app.mutsa_shoppingmall.Entity.Order;
import com.app.mutsa_shoppingmall.Entity.OrderItem;
import com.app.mutsa_shoppingmall.Entity.OrderStatus;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {

        @NotNull(message = "상품 ID는 필수입니다.")
        private Long productId;

        @NotNull(message = "수량은 필수입니다.")
        @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
        private Integer quantity;
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