package mutsa.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.api.domain.CartItem;

@NoArgsConstructor
@Getter
public class CartItemResponseDto {
    private Long cartItemId;
    private int quantity;
    private Long productId;
    private String productName;
    private int price;

    public static CartItemResponseDto from(CartItem cartItem) {
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.cartItemId = cartItem.getId();
        cartItemResponseDto.productId = cartItem.getProduct().getId();
        cartItemResponseDto.productName = cartItem.getProduct().getName();
        cartItemResponseDto.quantity = cartItem.getQuantity();
        cartItemResponseDto.price = cartItem.getProduct().getPrice();
        return cartItemResponseDto;
    }
}
