package mutsa.mutsa_week5_hw.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.global.code.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {

    private final BaseErrorCode errorCode;
}
