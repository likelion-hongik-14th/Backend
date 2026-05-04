package mutsa.api.dto;

import mutsa.api.domain.Product;
import lombok.Getter;
import mutsa.api.domain.ProductStatus;

@Getter
public class ProductResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private Integer stock;
    private String description;
    private ProductStatus status;

    public ProductResponseDto(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.description = product.getDescription();
        this.status = product.getStatus();
    }
}
