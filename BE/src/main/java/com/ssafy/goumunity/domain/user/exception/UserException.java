package com.ssafy.goumunity.domain.user.exception;

import com.ssafy.goumunity.common.exception.CustomException;

public class UserException extends CustomException {
    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
