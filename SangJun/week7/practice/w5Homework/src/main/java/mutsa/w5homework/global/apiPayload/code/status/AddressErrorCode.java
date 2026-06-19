package mutsa.w5homework.global.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.w5homework.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AddressErrorCode implements BaseErrorCode {
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "ADDRESS4001", "등록되지 않은 주소입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
