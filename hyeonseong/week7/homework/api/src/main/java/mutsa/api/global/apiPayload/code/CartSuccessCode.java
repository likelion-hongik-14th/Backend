package mutsa.api.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartSuccessCode implements BaseSuccessCode {
    CART_OK(HttpStatus.OK, "CART200_1", "장바구니 조회에 성공했습니다."),
    CART_ITEM_ADDED(HttpStatus.CREATED, "CART201_1", "장바구니에 상품이 추가되었습니다."),
    CART_ITEM_UPDATED(HttpStatus.OK, "CART200_2", "장바구니 상품 수량이 변경되었습니다."),
    CART_ITEM_DELETED(HttpStatus.OK, "CART200_3", "장바구니에서 상품이 삭제되었습니다."),
    CART_CLEARED(HttpStatus.OK, "CART200_4", "장바구니를 성공적으로 비웠습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
