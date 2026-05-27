package mutsa.api.global.apiPayload.handler;

import mutsa.api.global.apiPayload.ApiResponse;
import mutsa.api.global.apiPayload.code.BaseErrorCode;
import mutsa.api.global.apiPayload.code.GeneralErrorCode;
import mutsa.api.global.apiPayload.exception.NotFoundException;
import mutsa.api.global.apiPayload.exception.ProjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
//프로젝트 전체 Controller에서 발생한 예외를 여기서 잡아주는 클래스
/*
에러(예외)를 한곳에서 가로채어 사용자에게 예쁜 모양(ApiResponse 형식)으로
 응답을 내려주는 전역 예외 처리기 역할
*/
public class GeneralExceptionAdvice {
    // 프로젝트에서 발생한 예외 처리
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ApiResponse<Void>> handleMemberException(ProjectException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(errorCode, null));
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFoundException(NotFoundException ex) {
        BaseErrorCode code = GeneralErrorCode.NOT_FOUND;
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.onFailure(code, ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        BaseErrorCode code = GeneralErrorCode.BAD_REQUEST;
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.onFailure(code, ex.getMessage()));
    }

    // 그 외의 정의되지 않은 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.onFailure(code, ex.getMessage()));
    }

    // @Valid 어노테이션 검증 실패 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 검증 실패한 변수명과 실패 이유를 담을 Map
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        BaseErrorCode code = GeneralErrorCode.BAD_REQUEST;
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.onFailure(code, errors));
    }
}
