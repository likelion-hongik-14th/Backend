package mutsa.mutsa_week5_hw.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductSuccessCode implements BaseSuccessCode {

    // 상품 관련 성공 응답
    PRODUCT_CREATED(HttpStatus.CREATED, "PRODUCT_201_1", "상품 생성에 성공했습니다."),
    PRODUCT_FOUND(HttpStatus.OK, "PRODUCT_200_1", "상품 조회에 성공했습니다."),
    PRODUCT_UPDATED(HttpStatus.OK, "PRODUCT_200_2", "상품 수정에 성공했습니다."),
    PRODUCT_DELETED(HttpStatus.OK, "PRODUCT_200_3", "상품 삭제에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}