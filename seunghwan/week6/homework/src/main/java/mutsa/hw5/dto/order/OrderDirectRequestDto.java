package mutsa.hw5.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderDirectRequestDto {

    @NotNull
    private Long memberId;

    @NotNull
    private Long addressId;

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Long itemQuantity;
}
