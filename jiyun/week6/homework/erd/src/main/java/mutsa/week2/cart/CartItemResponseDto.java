package mutsa.week2.cart;

public record CartItemResponseDto(
        Long id,
        Long productId,
        String name,
        Long price,
        Integer quantity,
        String imageUrl
) {
    public static CartItemResponseDto from(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(),
                cartItem.getQuantity(),
                cartItem.getImageUrl()
        );
    }
}
