package com.ssafy.goumunity.domain.region.exception;

import com.ssafy.goumunity.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum RegionErrorCode implements ErrorCode {
    ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 지역이 존재합니다."),
    NO_REGION_DATA(HttpStatus.NOT_FOUND, "해당 지역을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}
