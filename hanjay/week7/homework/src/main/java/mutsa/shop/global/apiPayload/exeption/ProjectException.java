package mutsa.shop.global.apiPayload.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.shop.global.apiPayload.code.BaseErrorCode;

@Getter
@AllArgsConstructor
public class ProjectException extends RuntimeException {
    private final BaseErrorCode errorCode;
}
