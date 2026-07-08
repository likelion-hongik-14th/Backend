package mutsa.mutsa_week5_hw.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {

    // 상품 관련 에러 응답
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_404_1", "상품을 찾을 수 없습니다."),
    PRODUCT_ALREADY_EXISTS(HttpStatus.CONFLICT, "PRODUCT_409_1", "이미 존재하는 상품입니다."),
    INVALID_PRODUCT_PRICE(HttpStatus.BAD_REQUEST, "PRODUCT_400_1", "상품 가격이 올바르지 않습니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_400_2", "상품 재고가 부족합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
