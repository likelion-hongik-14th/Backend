package mutsa.hw5.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderSuccessCode implements BaseSuccessCode {
    ORDER_CREATED(HttpStatus.CREATED, "ORDER2001", "주문이 완료되었습니다."),
    ORDER_FOUND(HttpStatus.OK, "ORDER2000", "주문 조회 성공"),
    ORDER_STATUS_UPDATED(HttpStatus.OK, "ORDER2002", "주문 상태가 변경되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
