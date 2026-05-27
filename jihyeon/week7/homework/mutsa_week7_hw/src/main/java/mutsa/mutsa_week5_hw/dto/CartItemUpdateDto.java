package mutsa.mutsa_week5_hw.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemUpdateDto {

    //장바구니 속 상품 수량 변경
    private int quantity;
}
