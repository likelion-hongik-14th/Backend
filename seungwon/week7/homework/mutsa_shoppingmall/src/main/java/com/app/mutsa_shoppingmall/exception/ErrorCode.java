package com.app.mutsa_shoppingmall.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_4041", "상품을 찾을 수 없습니다."),

    // Cart
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_4041", "장바구니가 존재하지 않습니다."),
    CART_EMPTY(HttpStatus.BAD_REQUEST, "CART_4001", "장바구니가 비어있습니다."),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_4042", "장바구니 아이템이 존재하지 않습니다."),

    // Order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_4041", "주문을 찾을 수 없습니다."),
    ORDER_CANCEL_FORBIDDEN(HttpStatus.BAD_REQUEST, "ORDER_4002", "배송 완료된 주문은 취소할 수 없습니다."),

    // Stock
    STOCK_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "PRODUCT_4003", "재고가 부족합니다."),

    ORDER_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "ORDER_4003", "이미 취소된 주문입니다."),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "COMMON_4001", "수량은 1 이상이어야 합니다."),

    // Address
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "ADDRESS_4041", "배송지를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}