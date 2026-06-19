package mutsa.hw5.global.apiPayload.handler;

import mutsa.hw5.global.apiPayload.ApiResponse;
import mutsa.hw5.global.apiPayload.code.BaseErrorCode;
import mutsa.hw5.global.apiPayload.code.GeneralErrorCode;
import mutsa.hw5.global.apiPayload.exception.ProjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/*
모든 컨트롤러에서 발생하는 예외를 한 곳에서 가로채
ApiResponse 형식으로 변환해서 응답을 내려주는 전역 예외 처리기

예외 처리 우선순위: Spring은 더 구체적인 타입을 먼저 처리
  1. ProjectException      → 우리가 의도적으로 던진 도메인 에러
  2. MethodArgumentNotValidException → @Valid 검증 실패
  3. Exception             → 위 두 개에 해당 안 되는 모든 예외 (최후 방어선)
*/

@RestControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 여기서 처리
public class GeneralExceptionAdvice {

    // [1] Service에서 throw new ProjectException(XxxErrorCode.XXX)로 던진 예외 처리
    // ErrorCode에서 httpStatus, code, message를 꺼내 ApiResponse로 응답
    // error 필드는 null (추가 정보 없이 message만으로 충분)
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ApiResponse<Void>> handleMemberException(
            ProjectException e
    ) {
        BaseErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(errorCode, null));
    }

    // [3] 예상하지 못한 모든 예외 처리 (NullPointerException, DB 오류 등)
    // ex.getMessage()로 Java 예외 메시지를 꺼내 error 필드에 담음
    // → 개발자가 원인 파악할 수 있도록 힌트 제공
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(
            Exception ex
    ) {
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ApiResponse.onFailure(
                                code,
                                ex.getMessage() // Java 예외 객체가 기본으로 가진 메시지
                        )
                );
    }

    // [2] @Valid 검증 실패 예외 처리
    // getMessage()를 쓰면 Spring 내부의 긴 기술 메시지가 나와서,
    // getFieldErrors()로 실패한 필드명과 이유만 추출해 Map으로 담음
    // error 필드: { "productPrice": "1 이상이어야 합니다" } 형태
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage()); // 필드명 → 실패 이유
        });

        BaseErrorCode code = GeneralErrorCode.BAD_REQUEST;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ApiResponse.onFailure(code, errors));
    }
}