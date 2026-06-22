package mutsa.session5.global.apipayload.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.session5.global.apipayload.code.BaseSuccessCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductSuccessCode implements BaseSuccessCode {
    CREATE_PRODUCT_SUCCESS(HttpStatus.CREATED, "PRODUCT_201", "상품이 등록되었습니다."),
    GET_PRODUCT_SUCCESS(HttpStatus.OK, "PRODUCT_200_1", "상품 상세 정보 조회가 완료되었습니다."),
    GET_PRODUCT_LIST_SUCCESS(HttpStatus.OK, "PRODUCT_200_2", "상품 전체 목록 조회가 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
