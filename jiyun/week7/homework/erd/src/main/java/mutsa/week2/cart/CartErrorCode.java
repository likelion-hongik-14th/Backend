package mutsa.week2.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.week2.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartErrorCode implements BaseErrorCode {

    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "CART_4001", "수량은 1 이상이어야 합니다."),
    CART_FORBIDDEN(HttpStatus.FORBIDDEN, "CART_4031", "본인의 장바구니 상품만 접근할 수 있습니다."),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_4041", "해당 장바구니 상품은 존재하지 않습니다."),
    CART_ITEM_ALREADY_EXISTS(HttpStatus.CONFLICT, "CART_4092", "이미 장바구니에 담긴 상품입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
