package mutsa.homework.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_404_1", "해당 상품을 찾을 수 없습니다."),
    PRODUCT_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_400_1", "해당 상품의 재고가 부족합니다."),
    PRODUCT_ALREADY_EXISTS(HttpStatus.CONFLICT, "PRODUCT_409_1", "해당 상품이 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
