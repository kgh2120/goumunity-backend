package com.ssafy.goumunity.common.exception;

public class UserException extends RuntimeException{

    private final UserErrorCode errorCode;

    public UserException(UserErrorCode errorCode){
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
