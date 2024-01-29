package com.ssafy.goumunity.domain.feed.exception;

import com.ssafy.goumunity.common.exception.CustomException;

public class ReplyException extends CustomException {
    public ReplyException(ReplyErrorCode errorCode) {
        super(errorCode);
    }
}
