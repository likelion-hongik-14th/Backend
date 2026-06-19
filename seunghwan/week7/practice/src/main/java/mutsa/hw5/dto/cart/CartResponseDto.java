package mutsa.hw5.dto.cart;

import lombok.Builder;
import lombok.Getter;
import mutsa.hw5.domain.Cart;
import mutsa.hw5.dto.cartitem.CartItemResponseDto;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Builder
public class CartResponseDto {
    private Long cartId;
    private List<CartItemResponseDto> cartItems;
    private Long totalPrice;

    // Entity → DTO 변환
    // Service에서 호출하지만, 변환 로직을 DTO에 숨겨두어서 깔끔하게 유지
    public static CartResponseDto from(Cart cart) {
        // 정적 메서드를 쓰면 객체 없이 호출 가능
        // 그리고 builder()를 사용 가능하게 됨
        List<CartItemResponseDto> cartItems = cart.getCartItems().stream()
        // Cart에서 CartItem 리스트를 가져옴 + 리스트를 올려놓고(리스트 내에 객체를 순서대로 처리할 준비) CartItem들을 순서대로 처리

                .map(CartItemResponseDto::from)
                // 각 CartItem을 CartItemResponseDto로 변환

                .collect(Collectors.toList());
                // 변환된 것들을 다시 [CartItemResponseDto1, CartItemResponseDto2] 이런 식을 담음.

        Long totalPrice = cartItems.stream()
        // CartItemResponseDto 리스트를 다시 올려 놓음(리스트 내에 객체를 순서대로 처리할 준비)

                .mapToLong(item -> item.getProductPrice() * item.getItemQuantity())
                // 각 아이템마다 갯수 * 가격을 해서 Long 타입으로 변환

                .sum();
                // 위에서 변환한 가격들의 합을 구함

        return CartResponseDto.builder() // 메서드 체이닝으로 객체를 쉽게 반환
                .cartId(cart.getCartId())
                .cartItems(cartItems)
                .totalPrice(totalPrice)
                .build();
    }
}