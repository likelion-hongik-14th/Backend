package mutsa.hw5.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT404_1", "상품을 찾을 수 없습니다."),
    PRODUCT_HAS_ORDER(HttpStatus.BAD_REQUEST, "PRODUCT400_1", "주문 이력이 있는 상품은 삭제할 수 없습니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT400_2", "재고가 부족합니다."),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "PRODUCT400_3", "주문 수량은 1개 이상이어야 합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
