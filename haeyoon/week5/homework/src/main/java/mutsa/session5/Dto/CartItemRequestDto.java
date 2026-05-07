package mutsa.session5.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequestDto {
    @JsonProperty("user_id")
    private Long userId;

    private Long cart_id;
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long product_id;
    private String name;
    private Long price;
    @PositiveOrZero(message = "수량은 0개 이상이어야 합니다.")
    private int quantity;
}
