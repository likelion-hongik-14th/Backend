package com.app.mutsa_shoppingmall.exception.code;

import com.app.mutsa_shoppingmall.common.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartSuccessCode implements BaseSuccessCode {

    CART_FOUND(HttpStatus.OK, "CART2001", "장바구니 조회에 성공했습니다."),
    CART_ITEM_ADDED(HttpStatus.CREATED, "CART2011", "장바구니 상품 추가에 성공했습니다."),
    CART_ITEM_UPDATED(HttpStatus.OK, "CART2002", "장바구니 상품 수정에 성공했습니다."),
    CART_ITEM_DELETED(HttpStatus.OK, "CART2003", "장바구니 상품 삭제에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}