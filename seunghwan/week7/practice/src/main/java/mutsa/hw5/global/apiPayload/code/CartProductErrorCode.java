package mutsa.hw5.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartProductErrorCode implements BaseErrorCode {
    CART_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_PRODUCT404_1", "장바구니 상품을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
