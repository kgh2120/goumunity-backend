package com.ssafy.goumunity.common.exception.feed;

public class ParameterEmptyException extends RuntimeException{
    public ParameterEmptyException(Object T){
        super("[ParameterEmptyException]" + T.getClass());
    }
}
