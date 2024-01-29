package com.ssafy.goumunity.domain.feed.exception;

import com.ssafy.goumunity.common.exception.CustomException;

public class CommentException extends CustomException {
    public CommentException(CommentErrorCode errorCode) {
        super(errorCode);
    }
}
