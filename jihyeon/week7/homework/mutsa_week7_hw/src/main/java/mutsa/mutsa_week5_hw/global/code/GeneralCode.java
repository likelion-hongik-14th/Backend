package mutsa.mutsa_week5_hw.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GeneralCode {

    // ── Member ──────────────────────────────────────────────
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_4041", "회원을 찾을 수 없습니다."),

    // ── Product ─────────────────────────────────────────────
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_4041", "상품을 찾을 수 없습니다."),
    PRODUCT_ALREADY_EXISTS(HttpStatus.CONFLICT, "PRODUCT_4091", "이미 존재하는 상품입니다."),
    PRODUCT_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_4001", "상품 재고가 부족합니다."),
    PRODUCT_STOCK_ZERO(HttpStatus.BAD_REQUEST, "PRODUCT_4002", "재고가 0인 상품은 등록할 수 없습니다."),

    // ── Cart ────────────────────────────────────────────────
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_4041", "장바구니를 찾을 수 없습니다."),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_4042", "장바구니 상품을 찾을 수 없습니다."),
    CART_ITEM_ALREADY_EXISTS(HttpStatus.CONFLICT, "CART_4091", "이미 장바구니에 존재하는 상품입니다."),
    CART_ITEM_QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "CART_4001", "수량은 1개 이상이어야 합니다."),
    CART_EMPTY(HttpStatus.BAD_REQUEST, "CART_4002", "장바구니가 비어 있습니다."),

    // ── Address ─────────────────────────────────────────────
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "ADDRESS_4041", "배송지를 찾을 수 없습니다."),
    ADDRESS_FORBIDDEN(HttpStatus.FORBIDDEN, "ADDRESS_4031", "해당 배송지에 접근 권한이 없습니다."),
    ADDRESS_ALREADY_EXISTS(HttpStatus.CONFLICT, "ADDRESS_4091", "이미 동일한 배송지가 존재합니다."),
    ADDRESS_NO_CHANGES(HttpStatus.BAD_REQUEST, "ADDRESS_4001", "수정 내용이 기존 배송지 정보와 동일합니다."),

    // ── Order ────────────────────────────────────────────────
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_4041", "주문을 찾을 수 없습니다."),
    ORDER_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "ORDER_4001", "이미 취소된 주문입니다."),
    ORDER_CANCEL_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "ORDER_4002", "배송 완료된 주문은 취소할 수 없습니다."),
    ORDER_ALREADY_DELIVERED(HttpStatus.BAD_REQUEST, "ORDER_4003", "이미 배송 완료된 주문입니다."),
    ORDER_FORBIDDEN(HttpStatus.FORBIDDEN, "ORDER_4031", "해당 주문에 대한 권한이 없습니다."),
    ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_4042", "주문 상품을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
