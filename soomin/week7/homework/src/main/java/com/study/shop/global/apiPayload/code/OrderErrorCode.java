package com.study.shop.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements BaseErrorCode{

    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404_1", "주문을 찾을 수 없습니다."),
    ORDER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "ORDER403_1", "다른 회원의 주문에는 접근할 수 없습니다."),
    ORDER_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "ORDER400_1", "이미 취소된 주문입니다."),
    ORDER_DELIVERY_COMPLETED(HttpStatus.BAD_REQUEST, "ORDER400_2", "배송 완료된 주문은 취소할 수 없습니다."),
    DUPLICATE_CART_ITEM(HttpStatus.BAD_REQUEST, "ORDER400_3", "같은 장바구니 상품을 중복 주문할 수 없습니다."),
    ORDER_CANCELED_STATUS_CANNOT_CHANGE(HttpStatus.BAD_REQUEST, "ORDER400_4", "취소된 주문의 상태는 변경할 수 없습니다."),
    ORDER_DELIVERED_STATUS_CANNOT_CHANGE(HttpStatus.BAD_REQUEST, "ORDER400_5", "배송 완료된 주문의 상태는 변경할 수 없습니다."),
    ORDER_STATUS_CANNOT_CHANGE(HttpStatus.BAD_REQUEST, "ORDER400_6", "변경할 수 없는 주문 상태입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
