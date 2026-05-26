package mutsa.hw5.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderDirectRequestDto {

    @NotNull
    @Min(1)
    private Long memberId;

    @NotNull
    @Min(1)
    private Long addressId;

    @NotNull
    @Min(1)
    private Long productId;

    @NotNull
    @Min(1)
    private Long itemQuantity;
}
