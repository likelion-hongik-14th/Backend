package mutsa.w5homework.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.w5homework.domain.Order;
import mutsa.w5homework.domain.OrderItem;

import java.util.List;

public class OrderDto {
    @Getter
    @NoArgsConstructor
    public static class CartOrderRequest{
        private Long memberId;
        private Long addressId;
    }
    @Getter
    @NoArgsConstructor
    public static class DirectOrderRequest{
        private Long memberId;
        private Long productId;
        private Long count;
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
