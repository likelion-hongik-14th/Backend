package com.app.mutsa_shoppingmall.exception;

import com.app.mutsa_shoppingmall.common.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {
    private final BaseErrorCode errorCode;
}