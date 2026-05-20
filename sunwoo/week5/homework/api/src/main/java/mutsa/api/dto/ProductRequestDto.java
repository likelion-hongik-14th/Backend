package mutsa.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductRequestDto {
    @NotBlank(message = "Product name must not be blank")
    private String name;

    @Positive(message = "Price must be greater than 0")
    private int price;

    @PositiveOrZero(message = "Stock must be 0 or greater")
    private int stock;

    @NotBlank(message = "Description must not be blank")
    private String description;
}
