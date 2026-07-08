package com.study.shop.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class CartOrderRequest {

    @NotEmpty(message = "주문할 장바구니 상품을 선택해야 합니다.")
    private List<Long> cartItemIds;

    @NotNull(message = "배송지 ID는 필수입니다.")
    private Long addressId;

}
