package mutsa.hw5.dto.product;

import lombok.Getter;
import mutsa.hw5.domain.Product;

@Getter // get 메서드를 자동 생성 (ex.getName)
public class ProductRequestDto {
    private String productName;
    private Long productPrice;
    private Long productStock;

    // DTO → Entity 변환
    // DTO에서 변환을 통해 서비스 코드가 갈끔해짐
    public Product toEntity() {
        return Product.create(this.productName, this.productPrice, this.productStock);
    }
}