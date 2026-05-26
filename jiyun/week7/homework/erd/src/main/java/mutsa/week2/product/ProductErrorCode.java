package mutsa.week2.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.week2.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {

    INVALID_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_4001", "재고는 0 이상이어야 합니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_4041", "해당 상품은 존재하지 않습니다."),
    OUT_OF_STOCK(HttpStatus.CONFLICT, "PRODUCT_4091", "상품의 재고가 부족합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
