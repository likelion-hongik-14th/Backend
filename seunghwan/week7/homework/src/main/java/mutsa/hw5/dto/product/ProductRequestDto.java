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
    @Min(1) // 0원 상품 방지
    private Long productPrice;

    @NotNull
    @Min(0)
    private Long productStock;

    // DTO → Entity 변환
    // Service에서 호출하지만, 변환 로직을 DTO에 숨겨두어서 깔끔하게 유지
    public Product toEntity() {
        return Product.create(this.productName, this.productPrice, this.productStock);
    }
}