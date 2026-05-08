package mutsa.mutsa_week5_hw.dto;

import lombok.Getter;
import mutsa.mutsa_week5_hw.domain.Cart;

import java.util.List;

@Getter
public class CartResponseDto {

    //장바구니 조회
    private Long cartId;
    private List<CartItemResponseDto> items;
    private int totalPrice;

    public CartResponseDto(Cart cart) {
        this.cartId = cart.getId();

        this.items = cart.getCartItems()
                .stream()
                .map(CartItemResponseDto::new)
                .toList();

        this.totalPrice = cart.calculateTotalPrice();
    }
}
