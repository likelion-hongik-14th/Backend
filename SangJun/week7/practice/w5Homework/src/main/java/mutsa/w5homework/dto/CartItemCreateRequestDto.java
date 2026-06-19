package mutsa.w5homework.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CartItemCreateRequestDto {
    @NotNull(message = "회원 ID는 필수입니다.")
    private Long memberId;
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;
    @Min(value = 1, message = "수량은 최소 1개 이상입니다.")
    private Long count;
}
