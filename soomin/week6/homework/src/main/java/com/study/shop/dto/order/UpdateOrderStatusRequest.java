package com.study.shop.dto.order;

import com.study.shop.domain.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateOrderStatusRequest {

    @NotNull(message = "주문 상태는 필수입니다.")
    private OrderStatus status;

}
