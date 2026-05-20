package mutsa.hw5.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderCartRequestDto {

    @NotNull
    private Long memberId;

    @NotNull
    private Long addressId;
}
