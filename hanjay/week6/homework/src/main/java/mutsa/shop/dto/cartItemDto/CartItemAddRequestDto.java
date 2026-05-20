package mutsa.shop.dto.cartItemDto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CartItemAddRequestDto {
    private Long productId;
    private Long quantity;
}
