package mutsa.week2.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.week2.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SearchErrorCode implements BaseErrorCode {

    EMPTY_KEYWORD(HttpStatus.BAD_REQUEST, "SEARCH_4001", "검색 키워드는 비어있을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
