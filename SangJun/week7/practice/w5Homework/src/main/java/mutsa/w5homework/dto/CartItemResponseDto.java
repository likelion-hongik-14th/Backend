package mutsa.w5homework.dto;

import lombok.Getter;
import mutsa.w5homework.domain.CartItem;

@Getter
public class CartItemResponseDto {
    private Long id;
    private Long productId;
    private Long cartId;
    private Long count;

    public CartItemResponseDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.productId = cartItem.getProduct().getId();
        this.cartId = cartItem.getCart().getId();
        this.count = cartItem.getCount();
    }
}
