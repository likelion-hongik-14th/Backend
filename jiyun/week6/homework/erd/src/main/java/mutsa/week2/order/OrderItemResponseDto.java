package mutsa.week2.order;

public record OrderItemResponseDto(
        Long id,
        Long productId,
        String productName,
        Long price,
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
