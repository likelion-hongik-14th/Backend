package mutsa.shop.global.apiPayload.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.shop.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member4041","해당 회원을 찾을 수 없습니다."),
    USER_ALREADY_EXSISTS(HttpStatus.CONFLICT,"Member4091", "이미 존재하는 회원입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
