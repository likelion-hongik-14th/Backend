package mutsa.w5homework.global.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.w5homework.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

//Getter 어노테이션으로 오버라이딩을 인터페이스 오버라이딩을 처리함
@Getter
//모든 필드를 다 받는 생성자 생성
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER4001", "이미 가입된 이메일입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4002", "존재하지 않는 회원입니다");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    }
