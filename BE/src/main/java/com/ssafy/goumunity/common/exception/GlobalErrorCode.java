package com.ssafy.goumunity.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum GlobalErrorCode implements ErrorCode {
    BIND_ERROR(HttpStatus.BAD_REQUEST, "입력 값이 올바른 형식을 따르지 않았습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}
