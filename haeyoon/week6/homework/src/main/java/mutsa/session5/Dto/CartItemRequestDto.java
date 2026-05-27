package mutsa.session5.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CartItemRequestDto {
    private Long memberId;

    private Long cartId;
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;
    private String name;
    private Long price;
    @PositiveOrZero(message = "수량은 0개 이상이어야 합니다.")
    private int quantity;
}
