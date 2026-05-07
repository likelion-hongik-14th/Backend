package mutsa.shop.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CartResponseDto {
    private List<CartItemDto> items; // 장바구니 상품 목록
    private Long totalPrice;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class CartItemDto {
        private Long cartItemId;
        private String productName;
        private Long quantity;
        private Long price;
    }
}
