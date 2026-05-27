package mutsa.shop.global.apiPayload.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.shop.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND,"ORDER4041", "해당 주문이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
