package mutsa.w5homework.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.w5homework.domain.Order;
import mutsa.w5homework.domain.OrderItem;

import java.util.List;

public class OrderDto {
    @Getter
    @NoArgsConstructor
    public static class CartOrderRequest{
        @NotNull
        private Long memberId;
        @NotNull
        private Long addressId;
    }
    @Getter
    @NoArgsConstructor
    public static class DirectOrderRequest{
        @NotNull
        private Long memberId;
        @NotNull
        private Long productId;
        @Min(value = 1)
        private Long count;
        @NotNull
        private Long addressId;
    }

    @Getter
    public static class Response{
        private Long orderId;
        private String status;
        private String orderDate;
        private List<ItemResponse> orderItems;

        public Response(Order order){
            this.orderId = order.getId();
            this.status = order.getOrderStatus().toString();
            this.orderDate = order.getOrderDate().toString();
            this.orderItems = order.getOrderItems().stream()
                    .map(ItemResponse::new)
                    .toList();
        }

    }

    @Getter
    public static class ItemResponse{
        private String productName;
        private Long count;
        private Long orderPrice;

        public ItemResponse(OrderItem orderItem){
            this.productName = orderItem.getProduct().getName();
            this.count = orderItem.getCount();
            this.orderPrice = orderItem.getOrderPrice();
        }
    }
}
