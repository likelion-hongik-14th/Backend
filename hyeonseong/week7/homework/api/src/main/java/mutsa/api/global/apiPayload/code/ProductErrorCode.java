package mutsa.api.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT404_1", "존재하지 않거나 판매가 종료된 상품입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
