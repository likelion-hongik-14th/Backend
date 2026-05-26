package mutsa.hw5.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductSuccessCode implements BaseSuccessCode {
    PRODUCT_CREATED(HttpStatus.CREATED, "PRODUCT2001", "상품이 등록되었습니다."),
    PRODUCT_FOUND(HttpStatus.OK, "PRODUCT2000", "상품 조회 성공"),
    PRODUCT_UPDATED(HttpStatus.OK, "PRODUCT2002", "상품이 수정되었습니다."),
    PRODUCT_DELETED(HttpStatus.OK, "PRODUCT2003", "상품이 삭제되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
