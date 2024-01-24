package com.ssafy.goumunity.common.exception.feed;

public class DataExistException extends RuntimeException{
    public DataExistException(Object T){
        super("[DataExistException]" + T.getClass());
    }
}
