package mutsa.hw5.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import mutsa.hw5.domain.Product;

@Getter // get 메서드를 자동 생성 (ex.getName)
public class ProductRequestDto {
    @NotBlank // 문자열에 사용하는 어노테이션으로 빈 문자열, 공백, NULL 거부
    private String productName;

    @NotNull
    @Min(0) // 음수 거부
    private Long productPrice;

    @NotNull
    @Min(0)
    private Long productStock;

    // DTO → Entity 변환
    // DTO에서 변환을 통해 서비스 코드가 갈끔해짐
    public Product toEntity() {
        return Product.create(this.productName, this.productPrice, this.productStock);
    }
}