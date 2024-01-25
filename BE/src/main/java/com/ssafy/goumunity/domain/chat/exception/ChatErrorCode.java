package com.ssafy.goumunity.domain.chat.exception;

import com.ssafy.goumunity.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ChatErrorCode implements ErrorCode {
    ALREADY_CREATED_HASHTAG(HttpStatus.CONFLICT, "이미 등록된 해시태그입니다."),
    HASHTAG_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 해시태그입니다"),
    REGION_ID_NOT_MATCHED(HttpStatus.NOT_FOUND, "존재하지 않는 지역입니다.");
    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}
