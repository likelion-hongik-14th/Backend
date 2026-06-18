package mutsa.session5.global.apipayload.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartSuccessCode {
    ADD_ITEM_SUCCESS(HttpStatus.CREATED, "CART_201", "장바구니에 상품이 담겼습니다."),
    GET_CART_SUCCESS(HttpStatus.OK, "CART_200_1", "장바구니 목록 조회가 완료되었습니다."),
    UPDATE_QUANTITY_SUCCESS(HttpStatus.OK, "CART_200_2", "장바구니 수량이 변경되었습니다."),
    DELETE_ITEM_SUCCESS(HttpStatus.OK, "CART_200_3", "장바구니 상품이 삭제되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
