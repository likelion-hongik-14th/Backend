package mutsa.homework.dto.cart;

import mutsa.homework.domain.CartItem;

public record CartItemResponseDto(
    Long cartItemId,
    Long productId,
    String name,
    int price,
    int quantity

) {
    public static CartItemResponseDto from(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(),
                cartItem.getQuantity()
        );
    }
}
