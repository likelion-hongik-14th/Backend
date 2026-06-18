package mutsa.week2.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateFromCartRequestDto {

    @NotNull(message = "addressId는 필수입니다.")
    private Long addressId;
}
