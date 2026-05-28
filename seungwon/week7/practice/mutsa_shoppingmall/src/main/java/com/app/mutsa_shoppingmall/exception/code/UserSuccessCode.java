package com.app.mutsa_shoppingmall.exception.code;

import com.app.mutsa_shoppingmall.common.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserSuccessCode implements BaseSuccessCode {

    USER_FOUND(HttpStatus.OK, "USER2001", "사용자 조회에 성공했습니다."),
    USER_CREATED(HttpStatus.CREATED, "USER2011", "사용자 등록에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}