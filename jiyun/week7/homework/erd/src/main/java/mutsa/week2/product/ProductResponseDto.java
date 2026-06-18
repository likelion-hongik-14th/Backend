package mutsa.week2.product;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        BigDecimal price,
        Integer stock
) {
    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}
