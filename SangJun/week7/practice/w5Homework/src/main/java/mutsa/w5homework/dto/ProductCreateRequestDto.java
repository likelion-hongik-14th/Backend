package mutsa.w5homework.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductCreateRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Min(value = 1) //최소 가격 1원으로 설정
    private Long price;
    @Min(value = 1)
    private Long stock;
}
