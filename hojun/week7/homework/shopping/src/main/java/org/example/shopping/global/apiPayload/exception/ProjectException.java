package org.example.shopping.global.apiPayload.exception;

import lombok.Getter;
import org.example.shopping.global.apiPayload.code.BaseErrorCode;

@Getter
public class ProjectException extends RuntimeException{
    private final BaseErrorCode errorCode;

    public ProjectException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
