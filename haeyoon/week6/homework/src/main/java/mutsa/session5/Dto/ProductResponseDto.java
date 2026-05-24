package mutsa.session5.Dto;

import lombok.*;
import mutsa.session5.Entity.Product;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long productId;
    private String name;
    private Long price;
    private int stock;

    public static ProductResponseDto from(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
