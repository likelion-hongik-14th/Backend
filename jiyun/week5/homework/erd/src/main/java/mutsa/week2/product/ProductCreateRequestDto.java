package mutsa.week2.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequestDto {

    @NotBlank(message = "name은 필수입니다.")
    private String name;

    @NotNull(message = "price는 필수입니다.")
    @Min(value = 0, message = "price는 0 이상이어야 합니다.")
    private Long price;

    @NotNull(message = "stock은 필수입니다.")
    @Min(value = 0, message = "stock은 0 이상이어야 합니다.")
    private Integer stock;
}
