package mutsa.session5.global.apipayload.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.session5.global.apipayload.code.BaseErrorCode;
import org.h2.api.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartErrorCode implements BaseErrorCode {
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_404_1", "존재하지 않거나 빈 장바구니입니다."),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_404_2", "장바구니에서 해당 상품을 찾을 수 없습니다."),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "CART_400_1", "변경하려는 수량이 올바르지 않습니다. (1개 이상)"),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "CART_400_2", "선택하신 수량이 상품의 재고를 초과했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
