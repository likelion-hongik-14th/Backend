package org.example.shopping.global.apiPayload.code.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.shopping.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND,"ORDER_NOT_FOUND", "주문 내역을 찾을 수 없습니다."),
    CANNOT_CANCEL(HttpStatus.BAD_REQUEST, "CANNOT_CANCEL", "배송 전 주문만 취소할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
