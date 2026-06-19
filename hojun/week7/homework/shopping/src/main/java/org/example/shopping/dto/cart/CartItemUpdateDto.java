package org.example.shopping.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class CartItemUpdateDto {

    @NotNull(message = "수량은 필수 입력 항목입니다.")
    @Min(0)
    private int quantity;
}
