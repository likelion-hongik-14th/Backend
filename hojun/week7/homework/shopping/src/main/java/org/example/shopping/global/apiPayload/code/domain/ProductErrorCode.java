package org.example.shopping.global.apiPayload.code.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.shopping.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND,"PRODUCT_NOT_FOUND", "존재하지 않는 상품입니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "OUT_OF_STOCK", "상품의 재고가 부족합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
