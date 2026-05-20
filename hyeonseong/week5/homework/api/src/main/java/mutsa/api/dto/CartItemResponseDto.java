package mutsa.api.dto;

import lombok.Getter;
import mutsa.api.domain.CartItem;

@Getter
public class CartItemResponseDto {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;

    public CartItemResponseDto(CartItem cartItem){
        this.cartItemId = cartItem.getId();
        this.productId = cartItem.getProduct().getId();
        this.productName = cartItem.getProduct().getName();
        this.price = cartItem.getProduct().getPrice();
        this.quantity = cartItem.getQuantity();
    }
}
