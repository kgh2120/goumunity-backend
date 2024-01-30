package com.ssafy.goumunity.domain.feed.exception;

import com.ssafy.goumunity.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommentErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    FEED_NOT_MATCH(HttpStatus.BAD_REQUEST, "댓글이 게시글과 매칭되지 않습니다."),
    ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요가 존재합니다."),
    NO_LIKE_DATA(HttpStatus.NOT_FOUND, "좋아요가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}
