package mutsa.api.global.apiPayload.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mutsa.api.global.apiPayload.code.BaseErrorCode;

@Getter
@RequiredArgsConstructor
// ProjectException은 커스텀 예외들의 공통 부모 클래스 역할을 하는 예외 클래스
// 프로젝트에서 발생하는 다양한 예외들을 이 클래스를 상속하여 구현할 수 있다
public class ProjectException extends RuntimeException {
    private final BaseErrorCode errorCode;
}
