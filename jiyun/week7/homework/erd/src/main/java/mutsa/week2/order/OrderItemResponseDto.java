package mutsa.week2.order;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Long id,
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity
) {
    public static OrderItemResponseDto from(OrderItem item) {
        return new OrderItemResponseDto(
                item.getId(),
                item.getProduct().getId(),
                item.getProductName(),
                item.getPrice(),
                item.getQuantity()
        );
    }
}
