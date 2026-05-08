package musta_week5.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;  // ← 이걸로 교체

@Getter
@NoArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "상품명을 입력해주세요.")
    private String name;

    @NotNull(message = "가격을 입력해주세요.")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Integer price;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Long categoryId;

    private String description;

    private Integer stock;
}