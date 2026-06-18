package mutsa.homework.dto.order;

import mutsa.homework.domain.OrderItem;

public record OrderItemResponseDto(
        Long orderItemId,
        Long productId,
        String name,
        int price,
        int quantity

) {
    public static OrderItemResponseDto from(OrderItem orderItem){
        return new OrderItemResponseDto(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getOrderPrice(),
                orderItem.getQuantity()
        );
    }
}
