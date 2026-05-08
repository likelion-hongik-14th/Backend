package mutsa.mutsa_week5_hw.dto;

import lombok.Getter;
import mutsa.mutsa_week5_hw.domain.Product;

@Getter
public class ProductResponseDto {

    //상품 조회
    private Long id;
    private String name;
    private int price;
    private int stock;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
    }
}
