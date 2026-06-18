package mutsa.week2.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {
    OK(HttpStatus.OK, "COMMON_2000", "요청에 성공했습니다."),
    CREATED(HttpStatus.CREATED, "COMMON_2001", "리소스가 정상적으로 생성되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
