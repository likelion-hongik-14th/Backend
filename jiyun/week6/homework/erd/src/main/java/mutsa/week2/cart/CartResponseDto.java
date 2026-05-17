package mutsa.week2.cart;

import java.util.List;

public record CartResponseDto(
        List<CartItemResponseDto> cartItems,
        Long totalPrice
) {
    public static CartResponseDto of(List<CartItemResponseDto> cartItems) {
        long totalPrice = cartItems.stream()
                .mapToLong(item -> item.price() * item.quantity())
                .sum();
        return new CartResponseDto(cartItems, totalPrice);
    }
}
