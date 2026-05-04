package mutsa.api.dto;

import lombok.Getter;
import mutsa.api.domain.Cart;

import java.util.List;

@Getter
public class CartResponseDto {
    private Long cartId;
    private List<CartItemResponseDto> cartItems;
    private int totalPrice;

    public CartResponseDto(Cart cart){
        this.cartId = cart.getId();
        this.totalPrice = cart.calculateTotalPrice();
        this.cartItems = cart.getCartItems().stream()
                .map(CartItemResponseDto::new)
                .toList();
    }
}
