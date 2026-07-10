package mutsa.mutsa_week5_hw.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartItemSuccessCode implements BaseSuccessCode {

    // 장바구니 상품 관련 성공 응답
    CART_ITEM_ADDED(HttpStatus.CREATED, "CART_ITEM_201_1", "장바구니 상품 추가에 성공했습니다."),
    CART_ITEM_UPDATED(HttpStatus.OK, "CART_ITEM_200_1", "장바구니 상품 수량 변경에 성공했습니다."),
    CART_ITEM_DELETED(HttpStatus.OK, "CART_ITEM_200_2", "장바구니 상품 삭제에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
