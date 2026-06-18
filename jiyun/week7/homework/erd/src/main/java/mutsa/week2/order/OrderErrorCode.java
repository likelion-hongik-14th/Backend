package mutsa.week2.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.week2.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {

    EMPTY_CART(HttpStatus.BAD_REQUEST, "ORDER_4001", "장바구니가 비어있어 주문할 수 없습니다."),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "ORDER_4002", "주문 수량은 1 이상이어야 합니다."),
    ORDER_FORBIDDEN(HttpStatus.FORBIDDEN, "ORDER_4031", "본인의 주문만 접근할 수 있습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_4041", "해당 주문은 존재하지 않습니다."),
    INVALID_ORDER_STATUS(HttpStatus.CONFLICT, "ORDER_4091", "현재 주문 상태에서 수행할 수 없는 작업입니다."),
    ORDER_NOT_CANCELABLE(HttpStatus.CONFLICT, "ORDER_4092", "배송이 완료된 주문은 취소할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
