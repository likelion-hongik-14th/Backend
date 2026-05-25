package mutsa.api.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderSuccessCode implements BaseSuccessCode {
    ORDER_OK(HttpStatus.OK, "ORDER200_1", "주문 내역 조회에 성공했습니다."),
    ORDER_CREATED(HttpStatus.CREATED, "ORDER201_1", "주문이 성공적으로 완료되었습니다."),
    ORDER_CANCELED(HttpStatus.OK, "ORDER200_2", "주문이 정상적으로 취소되었습니다."),
    ORDER_DELIVERED(HttpStatus.OK, "ORDER200_3", "배송 완료 처리가 되었습니다."),
    ORDER_PAID(HttpStatus.OK, "ORDER200_4", "주문 결제가 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
