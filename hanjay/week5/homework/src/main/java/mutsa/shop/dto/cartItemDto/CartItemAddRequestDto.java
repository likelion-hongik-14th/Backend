package mutsa.shop.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CartItemAddRequestDto {
    private Long productId;
    private Long quantity;
}
