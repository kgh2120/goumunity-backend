package com.ssafy.goumunity.common.exception;

import lombok.Getter;

@Getter
public class InternalServerCaughtException extends InternalServerException {
    public InternalServerCaughtException(Throwable throwable, Object clazz) {
        super(throwable, clazz);
    }
}
