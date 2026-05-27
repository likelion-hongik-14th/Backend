package mutsa.shop.global.apiPayload.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.shop.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT4041", "존재하지 않는 상품입니다.");

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
