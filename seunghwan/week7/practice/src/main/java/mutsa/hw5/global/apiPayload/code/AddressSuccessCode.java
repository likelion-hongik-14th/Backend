package mutsa.hw5.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AddressSuccessCode implements BaseSuccessCode {
    ADDRESS_CREATED(HttpStatus.CREATED, "ADDRESS2001", "배송지가 등록되었습니다."),
    ADDRESS_FOUND(HttpStatus.OK, "ADDRESS2000", "배송지 목록 조회 성공"),
    ADDRESS_UPDATED(HttpStatus.OK, "ADDRESS2002", "배송지가 수정되었습니다."),
    ADDRESS_DELETED(HttpStatus.OK, "ADDRESS2003", "배송지가 삭제되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}