package mutsa.api.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AddressSuccessCode implements BaseSuccessCode {
    ADDRESS_OK(HttpStatus.OK, "ADDRESS200_1", "배송지 조회에 성공했습니다."),
    ADDRESS_CREATED(HttpStatus.CREATED, "ADDRESS201_1", "배송지가 성공적으로 등록되었습니다."),
    ADDRESS_UPDATED(HttpStatus.OK, "ADDRESS200_2", "배송지 정보가 성공적으로 수정되었습니다."),
    ADDRESS_DELETED(HttpStatus.OK, "ADDRESS200_3", "배송지가 성공적으로 삭제되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
