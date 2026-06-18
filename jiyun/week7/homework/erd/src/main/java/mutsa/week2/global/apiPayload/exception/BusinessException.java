package mutsa.week2.global.apiPayload.exception;

import lombok.Getter;
import mutsa.week2.global.apiPayload.code.BaseErrorCode;

@Getter
public class BusinessException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public BusinessException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
