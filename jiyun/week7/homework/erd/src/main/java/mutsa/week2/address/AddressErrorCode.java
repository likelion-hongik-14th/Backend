package mutsa.week2.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.week2.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AddressErrorCode implements BaseErrorCode {

    ADDRESS_FORBIDDEN(HttpStatus.FORBIDDEN, "ADDRESS_4031", "본인의 배송지만 접근할 수 있습니다."),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "ADDRESS_4041", "해당 배송지는 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
