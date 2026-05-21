package mutsa.homework.dto.cart;

import mutsa.homework.domain.Cart;

import java.util.List;

public record CartResponseDto(
        List<CartItemResponseDto> items,
        int totalPrice
) {
    public static CartResponseDto from(Cart cart){
        List<CartItemResponseDto> items = cart.getCartItems().stream()
                .map(CartItemResponseDto::from)
                .toList();

        return new CartResponseDto(items, cart.getTotalPrice());
    }
}
