package com.app.mutsa_shoppingmall.exception.code;

import com.app.mutsa_shoppingmall.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT4041", "존재하지 않는 상품 ID입니다."),
    PRODUCT_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT4001", "재고가 부족합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}