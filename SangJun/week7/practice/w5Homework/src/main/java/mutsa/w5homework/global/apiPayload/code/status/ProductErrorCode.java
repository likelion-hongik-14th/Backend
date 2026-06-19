package mutsa.w5homework.global.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.w5homework.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT4001", "존재하지 않는 상품입니다."),
    DUPLICATE_PRODUCT_NAME(HttpStatus.BAD_REQUEST, "PRODUCT4002", "중복된 상품 이름입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
