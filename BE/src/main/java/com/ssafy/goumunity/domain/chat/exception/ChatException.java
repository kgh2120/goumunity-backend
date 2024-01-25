package com.ssafy.goumunity.domain.chat.exception;

import com.ssafy.goumunity.common.exception.CustomException;

public class ChatException extends CustomException {
    public ChatException(ChatErrorCode errorCode) {
        super(errorCode);
    }
}
