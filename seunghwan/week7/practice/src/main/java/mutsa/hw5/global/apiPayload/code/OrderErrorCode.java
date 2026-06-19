package mutsa.hw5.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404_1", "주문을 찾을 수 없습니다."),
    INVALID_STATUS_TRANSITION(HttpStatus.BAD_REQUEST, "ORDER400_1", "해당 상태로 변경할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}