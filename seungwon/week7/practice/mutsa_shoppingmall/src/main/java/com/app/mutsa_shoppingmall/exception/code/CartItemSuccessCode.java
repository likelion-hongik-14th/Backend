package com.app.mutsa_shoppingmall.exception.code;

import com.app.mutsa_shoppingmall.common.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartItemSuccessCode implements BaseSuccessCode {

    CART_ITEM_FOUND(HttpStatus.OK, "CARTITEM2001", "장바구니 상품 조회에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}