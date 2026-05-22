package mutsa.week2.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "필수 항목이 누락되었습니다"),
    INVALID_TYPE(HttpStatus.BAD_REQUEST, "입력 형식이 올바르지 않습니다"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인 정보가 유효하지 않습니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "이 기능에 접근 권한이 없습니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품은 존재하지 않습니다"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다"),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 배송지는 존재하지 않습니다"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문은 존재하지 않습니다"),
    EMPTY_CART(HttpStatus.BAD_REQUEST, "장바구니가 비어있어 주문할 수 없습니다"),
    OUT_OF_STOCK(HttpStatus.CONFLICT, "상품의 재고가 부족합니다"),
    INVALID_ORDER_STATUS(HttpStatus.CONFLICT, "현재 주문 상태에서 수행할 수 없는 작업입니다"),
    ORDER_NOT_CANCELABLE(HttpStatus.CONFLICT, "배송이 완료된 주문은 취소할 수 없습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
