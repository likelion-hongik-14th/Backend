package mutsa.w5homework.global.exception;

import mutsa.w5homework.global.apiPayload.ApiResponse;
import mutsa.w5homework.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(GeneralException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        ApiResponse<Void> response = ApiResponse.onFailure(errorCode, null);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }
}
