package mutsa.api.dto;

import lombok.Getter;
import mutsa.api.domain.ProductStatus;

@Getter
public class ProductRequestDto {
    private String name;
    private Integer price;
    private Integer stock;
    private String description;
    private ProductStatus status;
}
