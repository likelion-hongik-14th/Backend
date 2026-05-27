package mutsa.mutsa_week5_hw.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {

    //상품 생성
    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @Min(value = 1, message = "가격은 1원 이상이어야 합니다.")
    private int price;

    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    private int stock;
}
