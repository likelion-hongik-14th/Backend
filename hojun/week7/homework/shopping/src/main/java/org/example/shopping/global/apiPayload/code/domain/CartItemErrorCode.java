package org.example.shopping.global.apiPayload.code.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.shopping.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartItemErrorCode implements BaseErrorCode {
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"CART_ITEM_NOT_FOUND", "장바구니에 담긴 상품을 찾을 수 없습니다."),
    INVALID_CART_ITEM_QUANTITY(HttpStatus.BAD_REQUEST, "INVALID_CART_ITEM_QUANTITY", "수량은 최소 1개 이상이어야 합니다.");
    private final HttpStatus httpStatus;

    private final String code;
    private final String message;
}
