package mutsa.api.global.apiPayload.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mutsa.api.global.apiPayload.code.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {
    private final BaseErrorCode errorCode;
}
