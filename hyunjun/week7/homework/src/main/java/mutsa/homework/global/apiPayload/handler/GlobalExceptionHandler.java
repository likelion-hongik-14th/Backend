package mutsa.homework.global.apiPayload.handler;

import mutsa.homework.global.apiPayload.GlobalResponse;
import mutsa.homework.global.apiPayload.code.BaseErrorCode;
import mutsa.homework.global.apiPayload.code.GeneralErrorCode;
import mutsa.homework.global.apiPayload.exception.ProjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<GlobalResponse<Void>> handleProjectException(
            ProjectException e
    ) {
        BaseErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(GlobalResponse.onFailure(errorCode,null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        BaseErrorCode errorCode = GeneralErrorCode.BAD_REQUEST;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(GlobalResponse.onFailure(errorCode, errors));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<String>> handleUndefinedException(
            Exception e
    ) {
        BaseErrorCode errorCode = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(GlobalResponse.onFailure(errorCode, e.getMessage()));
    }
}
