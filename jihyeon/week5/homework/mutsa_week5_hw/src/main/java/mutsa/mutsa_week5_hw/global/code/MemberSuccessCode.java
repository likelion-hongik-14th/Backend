package mutsa.mutsa_week5_hw.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    // 회원 관련 성공 응답
    MEMBER_CREATED(HttpStatus.CREATED, "MEMBER_201_1", "회원가입에 성공했습니다."),
    MEMBER_FOUND(HttpStatus.OK, "MEMBER_200_1", "회원 조회에 성공했습니다."),
    MEMBER_UPDATED(HttpStatus.OK, "MEMBER_200_2", "회원 정보 수정에 성공했습니다."),
    MEMBER_DELETED(HttpStatus.OK, "MEMBER_200_3", "회원 삭제에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
