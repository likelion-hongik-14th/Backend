package mutsa.mutsa_week5_hw.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartErrorCode implements BaseErrorCode {

    // 장바구니 관련 에러 응답
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_404_1", "장바구니를 찾을 수 없습니다."),
    INVALID_CART_QUANTITY(HttpStatus.BAD_REQUEST, "CART_400_1", "올바르지 않은 수량입니다.");;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
