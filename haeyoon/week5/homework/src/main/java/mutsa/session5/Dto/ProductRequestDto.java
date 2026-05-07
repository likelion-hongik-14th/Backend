package mutsa.session5.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    @NotBlank(message = "이름은 1자 이상 입력되어야 합니다.")
    private String name;
    @NotNull(message = "가격은 필수 항목입니다.")
    @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
    private Long price;
    @PositiveOrZero(message = "수량은 0개 이상이어야 합니다.")
    private int stock;
}
