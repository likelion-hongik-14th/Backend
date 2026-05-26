package mutsa.week2.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartAddItemRequestDto {
    @NotNull(message = "productId는 필수입니다.")
    private Long productId;

    @NotNull(message = "quantity는 필수입니다.")
    @Min(value = 1, message = "quantity는 1 이상이어야 합니다.")
    private Integer quantity;
}
