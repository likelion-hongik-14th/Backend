package mutsa.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DirectOrderRequestDto {
    private Long addressId;
    private Long productId;
    private Long quantity;
}
