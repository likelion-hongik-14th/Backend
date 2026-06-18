package mutsa.shop.dto.cartItemDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.shop.domain.CartItem;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItemResponseDto {
    private Long id;
    private Long productId;
    private Long quantity;

    // 생성자를 통해 엔티티를 DTO로 변환?
    public CartItemResponseDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.productId = cartItem.getProduct().getId();
        this.quantity = cartItem.getQuantity();
    }
}