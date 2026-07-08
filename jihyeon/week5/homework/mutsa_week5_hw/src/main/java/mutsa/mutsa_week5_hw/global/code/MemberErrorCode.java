package mutsa.mutsa_week5_hw.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    // 회원 관련 에러 응답
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "해당 회원을 찾을 수 없습니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER409_1", "이미 존재하는 회원입니다."),
    MEMBER_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER400_1", "아이디 혹은 비밀번호가 틀렸습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
