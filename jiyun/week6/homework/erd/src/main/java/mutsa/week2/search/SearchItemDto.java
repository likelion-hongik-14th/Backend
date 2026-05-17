package mutsa.week2.search;

import mutsa.week2.product.Product;

public record SearchItemDto(
        Long id,
        String name,
        Long price,
        String status
) {
    public static SearchItemDto from(Product product) {
        return new SearchItemDto(product.getId(), product.getName(), product.getPrice(), "SALE");
    }
}
