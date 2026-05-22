package mutsa.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.api.domain.Product;

@NoArgsConstructor
@Getter
public class ProductResponseDto {
    private Long id;
    private String name;
    private int price;
    private int stock;
    private String description;


    public static ProductResponseDto from(Product savedProduct) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.id = savedProduct.getId();
        responseDto.name = savedProduct.getName();
        responseDto.price = savedProduct.getPrice();
        responseDto.stock = savedProduct.getStock();
        responseDto.description = savedProduct.getDescription();
        return responseDto;
    }
}
