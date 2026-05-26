package mutsa.week2.cart;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDto(
        List<CartItemResponseDto> cartItems,
        BigDecimal totalPrice
) {
    public static CartResponseDto of(List<CartItemResponseDto> cartItems) {
        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartResponseDto(cartItems, totalPrice);
    }
}
