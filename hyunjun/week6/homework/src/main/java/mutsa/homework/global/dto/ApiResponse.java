package mutsa.homework.global.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private final String code;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("SUCESS", "요청이 성공적으로 처리되었습니다.", data);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
