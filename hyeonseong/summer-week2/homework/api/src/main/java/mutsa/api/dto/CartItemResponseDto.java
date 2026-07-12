package mutsa.api.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.api.domain.CartItem;

@Getter
@Builder
public class CartItemResponseDto {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private int price;
    private int quantity;

    // 정적 팩토리 메서드
    public static CartItemResponseDto of(CartItem cartItem) {
        return CartItemResponseDto.builder()
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getName())
                .price(cartItem.getProduct().getPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }
}