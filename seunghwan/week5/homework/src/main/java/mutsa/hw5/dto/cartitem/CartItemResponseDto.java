package mutsa.hw5.dto.cartitem;

import lombok.Builder;
import lombok.Getter;
import mutsa.hw5.domain.CartItem;

@Getter
@Builder
public class CartItemResponseDto {
    private Long itemId;
    private Long productId;
    private String productName;
    private Long productPrice;
    private int itemQuantity;

    // Entity → DTO 변환
    public static CartItemResponseDto from(CartItem cartItem) {
        return CartItemResponseDto.builder() // 메서드 체이닝으로 객체를 쉽게 반환
                .itemId(cartItem.getItemId())
                .productId(cartItem.getProduct().getProductId())
                .productName(cartItem.getProduct().getProductName())
                .productPrice(cartItem.getProduct().getProductPrice())
                .itemQuantity(cartItem.getItemQuantity())
                .build();
    }
}