package mutsa.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DirectOrderRequestDto {
    @NotNull(message = "Address id must not be null")
    private Long addressId;

    @NotNull(message = "Product id must not be null")
    private Long productId;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
