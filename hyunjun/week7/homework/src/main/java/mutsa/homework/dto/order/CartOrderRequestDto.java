package mutsa.homework.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CartOrderRequestDto(
        @NotEmpty(message = "장바구니에서 상품을 하나 이상 선택해야 합니다.")
        List<Long> cartItemIds,

        @NotNull(message = "주소 ID는 필수입니다.")
        Long addressId
) {}
