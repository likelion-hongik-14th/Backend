package mutsa.w5homework.dto;

import lombok.Getter;
import mutsa.w5homework.domain.CartItem;

@Getter
public class CartItemResponseDto {
    //responseDto에는 추가적으로 가드 어노테이션을 붙이지 않음(검증된 데이터를 보내주기 때문)
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
