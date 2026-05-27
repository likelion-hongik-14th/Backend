package com.app.mutsa_shoppingmall.exception.code;

import com.app.mutsa_shoppingmall.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartItemErrorCode implements BaseErrorCode {

    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CARTITEM4041", "장바구니 상품을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}