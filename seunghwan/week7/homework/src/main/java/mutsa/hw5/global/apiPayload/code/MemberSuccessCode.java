package mutsa.hw5.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {
    MEMBER_FOUND(HttpStatus.OK, "MEMBER2000", "회원 조회 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
