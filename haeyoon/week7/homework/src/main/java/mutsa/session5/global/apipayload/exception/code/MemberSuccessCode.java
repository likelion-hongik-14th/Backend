package mutsa.session5.global.apipayload.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberSuccessCode {

    CREATE_MEMBER_SUCCESS(HttpStatus.CREATED, "MEMBER_201", "회원가입이 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
