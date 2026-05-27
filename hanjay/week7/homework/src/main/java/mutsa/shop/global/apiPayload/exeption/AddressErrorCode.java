package mutsa.shop.global.apiPayload.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.shop.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum AddressErrorCode implements BaseErrorCode {

    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND,"ADDRESS4041", "해당 배송지를 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
