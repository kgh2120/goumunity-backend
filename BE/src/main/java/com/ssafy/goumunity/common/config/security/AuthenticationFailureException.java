package com.ssafy.goumunity.common.config.security;

import com.ssafy.goumunity.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class AuthenticationFailureException extends AuthenticationException {
    private final ErrorCode errorCode;

    public AuthenticationFailureException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
