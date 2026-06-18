package mutsa.homework.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddOrderRequestDto(

        @NotNull(message = "상품 ID는 필수입니다.")
        Long productId,

        @NotNull(message = "주소 ID는 필수입니다.")
        Long addressId,

        @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
        int quantity
) {}
