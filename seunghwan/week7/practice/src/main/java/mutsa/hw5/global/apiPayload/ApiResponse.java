package mutsa.hw5.global.apiPayload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.hw5.global.apiPayload.code.BaseErrorCode;
import mutsa.hw5.global.apiPayload.code.GeneralSuccessCode;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result", "error"})
// 모든 API 응답을 통일된 형식으로 내려주는 공통 응답 클래스
// 성공/실패 상관없이 항상 이 형태로 응답이 나감
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private Boolean isSuccess; // 성공 여부

    @JsonProperty("code")
    private String code; // 성공: "COMMON2000" 고정 / 실패: 도메인별 에러 코드 (ex. "PRODUCT404_1")

    @JsonProperty("message")
    private String message; // 성공: 컨트롤러에서 넘긴 메시지 / 실패: ErrorCode에 정의된 메시지

    @JsonProperty("result")
    private final T result; // 성공 시 실제 데이터 / 실패 시 null

    @JsonProperty("error")
    private Object error; // 성공 시 null / 실패 시 추가 에러 정보 (ex. @Valid 실패 필드 목록)

    // result가 있는 성공 응답 (조회, 수정 등)
    public static <T> ApiResponse<T> onSuccess(String message,T result) {
        return new ApiResponse<>(true, GeneralSuccessCode.OK.getCode(), message, result, null);
    }

    // result가 없는 성공 응답 (삭제 등)
    public static <T> ApiResponse<T> onSuccess(String message) {
        return new ApiResponse<>(true, GeneralSuccessCode.OK.getCode(), message, null, null);
    }

    // 실패 응답 — ErrorCode에서 code, message를 꺼내서 응답 구성
    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, Object error) {
        return new ApiResponse<>(false,errorCode.getCode(),errorCode.getMessage(),null,error);
    }

}