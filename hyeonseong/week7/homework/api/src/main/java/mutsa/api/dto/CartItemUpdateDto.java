package mutsa.api.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemUpdateDto {
    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int quantity;
}
