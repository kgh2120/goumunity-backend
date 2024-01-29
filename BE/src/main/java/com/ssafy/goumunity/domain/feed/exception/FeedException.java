package com.ssafy.goumunity.domain.feed.exception;

import com.ssafy.goumunity.common.exception.CustomException;

public class FeedException extends CustomException {
    public FeedException(FeedErrorCode errorCode) {
        super(errorCode);
    }
}
