package mutsa.api.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CartItemUpdateDto {
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
