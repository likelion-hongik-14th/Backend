package org.example.shopping.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralSuccessCode implements BaseCode{
    OK(HttpStatus.OK, "SUCCESS", "요청을 성공적으로 처리했습니다."),
    CREATED(HttpStatus.CREATED, "CREATED", "성공적으로 생성되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
