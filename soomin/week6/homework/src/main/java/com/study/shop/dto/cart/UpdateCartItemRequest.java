package com.study.shop.dto.cart;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class UpdateCartItemRequest {

    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private int quantity;

}
