package com.ssafy.goumunity.common.exception.feed;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message, Object T) {
        super("[ResourceNotFound] " + message + " " + T.getClass());
    }
}
