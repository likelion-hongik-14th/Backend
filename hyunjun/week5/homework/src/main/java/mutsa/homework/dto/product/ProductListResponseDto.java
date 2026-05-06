package mutsa.homework.dto.product;

import java.util.List;

public record ProductListResponseDto(
        List<ProductResponseDto> products,
        int totalCount
) {
    public static ProductListResponseDto from(List<ProductResponseDto> products) {
        return new ProductListResponseDto(products, products.size());
    }
}
