package com.ssafy.goumunity.common.exception;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String timeStamp;
    private HttpStatus httpStatus;
    private String errorName;
    private String errorMessage;
    private String path;

    public static ErrorResponse createErrorResponse(ErrorCode errorCode, String path) {
        return new ErrorResponse(
                Instant.now().toString(),
                errorCode.getHttpStatus(),
                errorCode.getErrorName(),
                errorCode.getErrorMessage(),
                path);
    }

    @Override
    public String toString() {
        return "{"
                + "timeStamp="
                + timeStamp
                + ", httpStatus="
                + httpStatus
                + ", errorName='"
                + errorName
                + '\''
                + ", errorMessage='"
                + errorMessage
                + '\''
                + ", path='"
                + path
                + '\''
                + '}';
    }
}
