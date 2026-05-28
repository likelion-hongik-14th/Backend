package com.app.mutsa_shoppingmall.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GeneralSuccessCode {
    OK("200", "성공");

    private final String code;
    private final String message;
}