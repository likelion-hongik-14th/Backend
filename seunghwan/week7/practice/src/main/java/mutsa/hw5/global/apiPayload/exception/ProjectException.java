package mutsa.hw5.global.apiPayload.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mutsa.hw5.global.apiPayload.code.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {

    // 도메인별 ErrorCode (ex. ProductErrorCode.PRODUCT_NOT_FOUND)를 담아서 던지는 커스텀 예외
    // Service에서 throw new ProjectException(XxxErrorCode.XXX) 형태로 사용
    // GeneralExceptionAdvice가 이걸 낚아채서 ApiResponse 형태로 응답 변환
    private final BaseErrorCode errorCode;
}