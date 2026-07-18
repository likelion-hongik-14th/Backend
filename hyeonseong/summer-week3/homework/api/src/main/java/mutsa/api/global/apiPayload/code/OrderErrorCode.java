package mutsa.api.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404_1", "주문 내역을 찾을 수 없습니다."),
    CART_EMPTY(HttpStatus.BAD_REQUEST, "ORDER400_1", "장바구니가 비어있어 주문할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
