package mutsa.api.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.api.domain.Cart;

import java.util.List;

@Getter
@Builder
public class CartResponseDto {
    private Long cartId;
    private List<CartItemResponseDto> cartItems;
    private int totalPrice;

    public static CartResponseDto of(Cart cart) {
        return CartResponseDto.builder()
                .cartId(cart.getId())
                .totalPrice(cart.calculateTotalPrice())
                .cartItems(cart.getCartItems().stream()
                        .map(CartItemResponseDto::of)
                        .toList())
                .build();
    }
}