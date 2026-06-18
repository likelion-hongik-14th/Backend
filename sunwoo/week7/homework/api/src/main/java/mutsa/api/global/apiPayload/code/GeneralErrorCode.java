package mutsa.api.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralErrorCode implements BaseErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_4001", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_4011", "인증되지 않았습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_4031", "접근이 금지되었습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_4041", "해당 리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_5001", "서버 내부 에러가 발생했습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_4041", "유저를 찾을 수 없습니다."),

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_4041", "상품을 찾을 수 없습니다."),
    PRODUCT_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_4001", "상품 재고가 부족합니다."),

    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_4041", "장바구니를 찾을 수 없습니다."),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_4042", "장바구니 상품을 찾을 수 없습니다."),
    CART_EMPTY(HttpStatus.BAD_REQUEST, "CART_4001", "장바구니가 비어 있습니다."),
    CART_QUANTITY_EXCEEDS_STOCK(HttpStatus.BAD_REQUEST, "CART_4002", "장바구니 상품 수량이 재고보다 많습니다."),

    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_4041", "주문을 찾을 수 없습니다."),
    ORDER_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "ORDER_4001", "이미 취소된 주문입니다."),
    ORDER_DELIVERY_COMPLETED(HttpStatus.BAD_REQUEST, "ORDER_4002", "배송 완료된 주문은 변경할 수 없습니다."),

    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "ADDRESS_4041", "주소를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
