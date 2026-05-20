package mutsa.hw5.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CartItemUpdateDto {
    @NotNull
    @Min(1)
    private Long itemQuantity;
}