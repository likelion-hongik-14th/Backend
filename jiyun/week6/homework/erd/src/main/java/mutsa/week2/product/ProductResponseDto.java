package mutsa.week2.product;

public record ProductResponseDto(
        Long id,
        String name,
        Long price,
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
