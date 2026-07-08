package mutsa.mutsa_week5_hw.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartSuccessCode implements BaseSuccessCode {

    // 장바구니 관련 성공 응답
    CART_FOUND(HttpStatus.OK, "CART_200_1", "장바구니 조회에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
