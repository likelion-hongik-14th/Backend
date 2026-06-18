package mutsa.week2.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.week2.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_4041", "해당 회원은 존재하지 않습니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER_4091", "이미 가입된 이메일입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
