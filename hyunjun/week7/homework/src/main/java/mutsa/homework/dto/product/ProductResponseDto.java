package mutsa.homework.dto.product;

import mutsa.homework.domain.Product;

public record ProductResponseDto(
        Long productId,
        String name,
        int price,
        int stock
) {
    public static ProductResponseDto from(Product product){
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}
