package mutsa.hw5.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartSuccessCode implements BaseSuccessCode {
    CART_FOUND(HttpStatus.OK, "CART2000", "장바구니 조회 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
