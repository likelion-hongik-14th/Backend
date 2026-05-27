package mutsa.api.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.api.domain.Product;
import mutsa.api.domain.ProductStatus;

@Getter
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private int price;
    private int stock;
    private String description;
    private ProductStatus status;

    public static ProductResponseDto of(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                .status(product.getStatus())
                .build();
    }
}
