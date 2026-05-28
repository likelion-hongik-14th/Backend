package mutsa.mutsa_week5_hw.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.mutsa_week5_hw.domain.Cart;

import java.util.List;

@Getter
@Builder // 수정
public class CartResponseDto {

    //장바구니 조회
    private Long cartId;
    private List<CartItemResponseDto> items;
    private int totalPrice;

    public static CartResponseDto from(Cart cart) { // 수정
        return CartResponseDto.builder()
                .cartId(cart.getId())
                .items(
                        cart.getCartItems()
                                .stream()
                                .map(CartItemResponseDto::from)
                                .toList()
                )
                .totalPrice(cart.calculateTotalPrice())
                .build();
    }
}
