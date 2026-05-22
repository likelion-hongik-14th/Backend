package mutsa.shop.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CartItemUpdateRequestDto {
    private Long quantity;
}
