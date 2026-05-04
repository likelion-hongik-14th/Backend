package mutsa.w5homework.dto;

import lombok.Getter;
import mutsa.w5homework.domain.Cart;

import java.util.List;

@Getter
public class CartResponseDto {
    private Long id;
    private Long memberId;
    private List<CartItemResponseDto> cartItems;
    private Long totalPrice;
    private Long totalQuantity;

    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.memberId = cart.getMember().getId();
        this.cartItems = cart.getCartItems().stream()
                .map(CartItemResponseDto::new)
                .toList();
        this.totalPrice = cart.getTotalPrice();
        this.totalQuantity = cart.getTotalQuantity();
    }
}
