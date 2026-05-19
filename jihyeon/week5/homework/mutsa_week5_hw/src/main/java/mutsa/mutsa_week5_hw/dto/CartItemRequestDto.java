package mutsa.mutsa_week5_hw.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemRequestDto {

    //장바구니에 상품 추가
    @NotNull(message = "상품 ID는 필수입니다.") // 수정: 유효성 검사 추가
    private Long productId;

    @Min(value = 1, message = "수량은 1 이상이어야 합니다.") // 수정: 유효성 검사 추가
    private int quantity;
}
