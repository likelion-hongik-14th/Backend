package mutsa.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.api.domain.Cart;

import java.util.List;
@NoArgsConstructor
@Getter
public class CartResponseDto {
    private Long cartId;
    private List<CartItemResponseDto> cartItems;
    private int totalPrice;


    public static CartResponseDto from(Cart cart) {
        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.cartId = cart.getId();
        cartResponseDto.cartItems = cart.getCartItems().stream()
                .map(CartItemResponseDto::from)
                .toList();
        cartResponseDto.totalPrice = cart.calculateTotalPrice();
        return cartResponseDto;
    }
}
