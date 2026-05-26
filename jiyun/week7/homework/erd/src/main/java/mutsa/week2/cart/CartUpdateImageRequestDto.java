package mutsa.week2.cart;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartUpdateImageRequestDto {
    @NotBlank(message = "imageUrl은 필수입니다.")
    private String imageUrl;
}
