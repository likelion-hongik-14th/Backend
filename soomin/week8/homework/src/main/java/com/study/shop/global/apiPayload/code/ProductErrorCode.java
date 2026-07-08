package com.study.shop.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode{

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT404_1", "상품을 찾을 수 없습니다."),
    PRODUCT_DELETED(HttpStatus.BAD_REQUEST, "PRODUCT400_1", "삭제된 상품입니다."),
    PRODUCT_STOCK_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "PRODUCT400_2", "상품 재고가 부족합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
