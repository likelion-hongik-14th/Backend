package mutsa.session5.global.apipayload.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.session5.global.apipayload.code.BaseSuccessCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderSuccessCode implements BaseSuccessCode {
    CREATE_ORDER_SUCCESS(HttpStatus.CREATED, "ORDER_201", "주문이 완료되었습니다."),
    GET_ORDER_SUCCESS(HttpStatus.OK, "ORDER_200_1", "주문 상세 내역 조회가 완료되었습니다."),
    COMPLETE_DELIVERY_SUCCESS(HttpStatus.OK, "ORDER_200_2", "배송 완료 처리되었습니다."),
    CONFIRM_PAYMENT_SUCCESS(HttpStatus.OK, "ORDER_200_3", "결제 승인이 완료되었습니다."),
    CANCEL_ORDER_SUCCESS(HttpStatus.OK, "ORDER_200_4", "주문이 취소되고 재고가 복구되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
