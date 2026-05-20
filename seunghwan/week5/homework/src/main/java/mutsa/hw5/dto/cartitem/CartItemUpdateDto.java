package mutsa.hw5.dto.cartitem;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class CartItemUpdateDto {
    @Min(1) // 0이하는 입력 불가
    private int itemQuantity;
}