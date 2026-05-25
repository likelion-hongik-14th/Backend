package mutsa.hw5.global.apiPayload.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mutsa.hw5.global.apiPayload.code.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {

    private final BaseErrorCode errorCode;
}