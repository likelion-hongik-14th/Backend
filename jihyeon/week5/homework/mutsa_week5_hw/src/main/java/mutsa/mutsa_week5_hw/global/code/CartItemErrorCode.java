package mutsa.mutsa_week5_hw.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartItemErrorCode implements BaseErrorCode {

    // 장바구니 상품 관련 에러 응답
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_ITEM_404_1", "장바구니 상품을 찾을 수 없습니다."),
    PRODUCT_ALREADY_EXISTS(HttpStatus.CONFLICT, "CART_ITEM_409_1", "이미 장바구니에 담긴 상품입니다."),
    INVALID_ITEM_QUANTITY(HttpStatus.BAD_REQUEST, "CART_ITEM_400_1", "상품 수량이 올바르지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
