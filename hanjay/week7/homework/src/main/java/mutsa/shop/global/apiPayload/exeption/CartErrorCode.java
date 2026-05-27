package mutsa.shop.global.apiPayload.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.shop.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor

public enum CartErrorCode implements BaseErrorCode {

    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART4041", "장바구니 아이템을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
