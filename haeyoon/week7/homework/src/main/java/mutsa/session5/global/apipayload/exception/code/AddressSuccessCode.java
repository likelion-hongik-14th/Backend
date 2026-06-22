package mutsa.session5.global.apipayload.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.session5.global.apipayload.code.BaseSuccessCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AddressSuccessCode implements BaseSuccessCode {
    CREATE_ADDRESS_SUCCESS(HttpStatus.CREATED, "ADDRESS_201", "배송지가 성공적으로 등록되었습니다."),
    GET_ADDRESS_LIST_SUCCESS(HttpStatus.OK, "ADDRESS_200_1", "배송지 목록 조회가 완료되었습니다."),
    UPDATE_ADDRESS_SUCCESS(HttpStatus.OK, "ADDRESS_200_2", "배송지 정보 수정이 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
