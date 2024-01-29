package com.ssafy.goumunity.common.exception.feed;

public class RegionNotValidatedException extends RuntimeException {
    public RegionNotValidatedException(String message, Object T) {
        super("[RegionNotValidated] " + message + T.getClass());
    }
}
