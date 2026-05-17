package mutsa.week2.common.error;

import java.time.LocalDateTime;

public record ErrorResponse(
        String errorCode,
        String message,
        int status,
        LocalDateTime timestamp,
        String path
) {
    public static ErrorResponse of(ErrorCode errorCode, String path) {
        return of(errorCode, errorCode.getMessage(), path);
    }

    public static ErrorResponse of(ErrorCode errorCode, String message, String path) {
        return new ErrorResponse(
                errorCode.name(),
                message,
                errorCode.getStatus().value(),
                LocalDateTime.now(),
                path
        );
    }
}
