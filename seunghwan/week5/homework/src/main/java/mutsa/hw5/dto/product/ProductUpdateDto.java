package mutsa.hw5.dto.product;

import lombok.Getter;

@Getter
public class ProductUpdateDto {
    private String productName;
    private Long productPrice;
    private Long productStock;
}
