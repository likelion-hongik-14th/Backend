package mutsa.session5.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import mutsa.session5.Entity.CartItem;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CartItemResponseDto {
    private Long cartId;
    private Long productId;
    private String name;
    private Long price;
    private int quantity;
    private Long totalPrice;

    public static CartItemResponseDto from(CartItem cartItem) {
        return CartItemResponseDto.builder()
                .cartId(cartItem.getCartItemId())
                .name(cartItem.getProduct().getName())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.calculatePrice())
                .build();
    }
}