package mutsa.w5homework.dto;

import lombok.Getter;
import mutsa.w5homework.domain.Product;

@Getter
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Long stock;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
    }
}
