package mutsa.session5.global.apipayload.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mutsa.session5.global.apipayload.code.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {
    private final BaseErrorCode errorCode;
}
