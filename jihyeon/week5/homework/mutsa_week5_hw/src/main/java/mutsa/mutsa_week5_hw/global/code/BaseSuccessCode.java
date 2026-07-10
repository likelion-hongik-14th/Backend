package mutsa.mutsa_week5_hw.global.code;

import org.springframework.http.HttpStatus;

public interface BaseSuccessCode {

    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
