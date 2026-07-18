package mutsa.api.global.apiPayload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.api.global.apiPayload.code.BaseErrorCode;
import mutsa.api.global.apiPayload.code.BaseSuccessCode;
import mutsa.api.global.apiPayload.code.GeneralSuccessCode;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result", "error"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private Boolean isSuccess;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("result")
    private final T result;

    @JsonProperty("error")
    private Object error;

    public static <T> ApiResponse<T> onSuccess(String message, T result) {
        return new ApiResponse<>(true, GeneralSuccessCode.OK.getCode(), message, result, null);
    }

    public static <T> ApiResponse<T> onSuccess(String message){
        return new ApiResponse<>(true, GeneralSuccessCode.OK.getCode(), message, null, null);
    }

    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, Object error) {
        return new ApiResponse<>(false,errorCode.getCode(),errorCode.getMessage(),null,error);
    }

    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode successCode, T result){
        return new ApiResponse<>(true, successCode.getCode(), successCode.getMessage(), result, null);
    }

    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode successCode){
        return new ApiResponse<>(true, successCode.getCode(), successCode.getMessage(), null, null);
    }

}
