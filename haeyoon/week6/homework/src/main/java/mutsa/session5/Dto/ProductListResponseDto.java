package mutsa.session5.Dto;

import lombok.*;
import mutsa.session5.Entity.Product;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductListResponseDto {
    private List<ProductResponseDto> products;

    public static ProductListResponseDto from(List<Product> products) {
        return ProductListResponseDto.builder()
                .products(products.stream()
                        .map(ProductResponseDto::from)
                        .toList())
                .build();
    }
}