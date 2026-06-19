package mutsa.w5homework.global.exception;

import lombok.Getter;
import mutsa.w5homework.global.apiPayload.code.BaseErrorCode;

@Getter
public class GeneralException extends RuntimeException{
    private final BaseErrorCode errorCode;
    public GeneralException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
