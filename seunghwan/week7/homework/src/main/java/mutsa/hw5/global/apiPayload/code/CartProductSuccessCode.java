package mutsa.hw5.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartProductSuccessCode implements BaseSuccessCode {
    CART_ITEM_ADDED(HttpStatus.CREATED, "CART_PRODUCT2001", "장바구니에 상품이 추가되었습니다."),
    CART_ITEM_UPDATED(HttpStatus.OK, "CART_PRODUCT2002", "장바구니 수량이 변경되었습니다."),
    CART_ITEM_DELETED(HttpStatus.OK, "CART_PRODUCT2003", "장바구니 상품이 삭제되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
