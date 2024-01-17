package com.ssafy.goumunity.common.exception;

public class CustomException extends RuntimeException{

    private final CustomErrorCode errorCode;

    public CustomException(CustomErrorCode errorCode){
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public CustomErrorCode getErrorCode(){
        return this.errorCode;
    }
}
