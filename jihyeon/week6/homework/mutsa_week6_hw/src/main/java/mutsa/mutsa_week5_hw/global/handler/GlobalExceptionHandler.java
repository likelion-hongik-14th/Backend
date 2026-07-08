package mutsa.mutsa_week5_hw.global.handler;

import mutsa.mutsa_week5_hw.global.exception.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(GeneralException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", e.getCode().getCode());
        body.put("message", e.getCode().getMessage());
        return ResponseEntity.status(e.getCode().getHttpStatus()).body(body);
    }
}
