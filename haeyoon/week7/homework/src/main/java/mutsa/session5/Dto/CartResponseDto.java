package mutsa.session5.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import mutsa.session5.Entity.Cart;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CartResponseDto {
    private Long cartId;
    private Long memberId;
    private List<CartItemResponseDto> cartItems;
    private Long totalPrice;

    public static CartResponseDto from(Cart cart) {
        return CartResponseDto.builder()
                .cartId(cart.getCartId()) // 👈 Cart의 @Id 필드인 cartId 매핑 완벽 일치!
                .memberId(cart.getMember().getMemberId()) // 👈 Member 엔티티의 memberId 매핑
                .cartItems(cart.getCartItems().stream()
                        .map(CartItemResponseDto::from) // 👈 아까 수정한 CartItemResponseDto 활용
                        .toList())
                .totalPrice(cart.calculateTotalPrice()) // 👈 엔티티 내부 메서드 호출로 깔끔하게 처리!
                .build();
    }
}