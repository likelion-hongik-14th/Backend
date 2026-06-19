package mutsa.w5homework.global.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.w5homework.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum OrderErrorCode implements BaseErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER4001", "유효하지 않은 주문번호입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
