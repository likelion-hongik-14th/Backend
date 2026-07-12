package mutsa.api.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductSuccessCode implements BaseSuccessCode {
    PRODUCT_OK(HttpStatus.OK, "PRODUCT200_1", "상품 조회에 성공했습니다."),
    PRODUCT_CREATED(HttpStatus.CREATED, "PRODUCT201_1", "상품이 성공적으로 등록되었습니다."),
    PRODUCT_UPDATED(HttpStatus.OK, "PRODUCT200_2", "상품 정보가 성공적으로 수정되었습니다."),
    PRODUCT_DELETED(HttpStatus.OK, "PRODUCT200_3", "상품이 성공적으로 삭제되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
