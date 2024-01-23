package com.ssafy.goumunity.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CustomErrorCode implements ErrorCode {
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "이메일이 존재하지 않습니다."),
    EXIST_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    UNABLE_TO_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "인증 이메일 발송에 실패했습니다."),
    NO_INPUT_FOR_MODIFY_USER_INFO(HttpStatus.BAD_REQUEST, "회원 정보를 수정하기 위한 정보를 전달하지 않았습니다."),
    LOCAL_FILE_UPLOAD_FAILED(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "잠시후 시도해주세요. 계속해서 문제가 발생한 경우 관리자 이메일(goumunity@gmail.com)로 연락주세요."),
    FILE_IS_NOT_IMAGE_TYPE(HttpStatus.BAD_REQUEST, "전송한 파일이 이미지 타입이 아닙니다."),

    INTERNAL_SERVER_ERROR_CODE(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "잠시후 시도해주세요. 계속해서 문제가 발생한 경우 관리자 이메일(goumunity@gmail.com)로 연락주세요."),
    ;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}
