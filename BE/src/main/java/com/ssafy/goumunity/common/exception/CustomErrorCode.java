package com.ssafy.goumunity.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CustomErrorCode implements ErrorCode {
    // User 관련 에러
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "이메일이 존재하지 않습니다."),
    EXIST_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    UNABLE_TO_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "인증 이메일 발송에 실패했습니다."),
    NO_INPUT_FOR_MODIFY_USER_INFO(HttpStatus.BAD_REQUEST, "회원 정보를 수정하기 위한 정보를 전달하지 않았습니다."),
    INVALID_USER(HttpStatus.FORBIDDEN, "권한이 없는 사용자입니다."),

    // Comment 관련 에러
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    FEED_NOT_MATCH(HttpStatus.BAD_REQUEST, "댓글이 게시글과 매칭되지 않습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}
