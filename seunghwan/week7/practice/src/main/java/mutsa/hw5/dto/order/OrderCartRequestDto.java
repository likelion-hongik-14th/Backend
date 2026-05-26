package mutsa.hw5.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderCartRequestDto {

    @NotNull
    @Min(1)
    private Long memberId;

    @NotNull
    @Min(1)
    private Long addressId;
}
