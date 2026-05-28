package com.study.shop.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartErrorCode implements BaseErrorCode{

    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART404_1", "장바구니를 찾을 수 없습니다."),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART404_2", "장바구니 상품을 찾을 수 없습니다."),
    CART_ACCESS_DENIED(HttpStatus.FORBIDDEN, "CART403_1", "다른 회원의 장바구니에는 접근할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
