package mutsa.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CartItemRequestDto {
    private Long productId;
    private int quantity;
}
