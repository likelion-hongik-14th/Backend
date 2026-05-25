package mutsa.hw5.dto.product;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class ProductUpdateDto {
    private String productName;
    @Min(0)
    private Long productPrice;
    @Min(0)
    private Long productStock;
}
