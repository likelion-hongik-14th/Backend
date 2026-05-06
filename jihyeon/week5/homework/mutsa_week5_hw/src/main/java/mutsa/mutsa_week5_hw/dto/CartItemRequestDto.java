package mutsa.mutsa_week5_hw.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemRequestDto {

    //장바구니에 상품 추가
    private Long productId;
    private int quantity;
}
