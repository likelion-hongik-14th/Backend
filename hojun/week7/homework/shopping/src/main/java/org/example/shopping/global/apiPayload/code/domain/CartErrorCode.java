package org.example.shopping.global.apiPayload.code.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.shopping.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartErrorCode implements BaseErrorCode {
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_NOT_FOUND", "해당 회원의 장바구니가 존재하지 않습니다."),
    EMPTY_CART(HttpStatus.BAD_REQUEST, "EMPTY_CART", "비어있는 장바구니입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
