package mutsa.homework.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {

    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_404_1", "해당 주문을 찾을 수 없습니다."),
    CANCEL_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "ORDER_400_1", "배송 중이거나 완료된 주문은 취소할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
