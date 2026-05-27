package com.study.shop.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "회원을 찾을 수 없습니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "MEMBER400_1", "이미 가입된 이메일입니다"),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "MEMBER401_1", "이메일 또는 비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
