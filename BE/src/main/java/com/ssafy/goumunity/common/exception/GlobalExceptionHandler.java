package com.ssafy.goumunity.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.ssafy.goumunity.common.exception.GlobalErrorCode.BIND_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(CustomException e, HttpServletRequest request) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.createErrorResponse(e.getErrorCode(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request) {
        return ResponseEntity.status(BIND_ERROR.getHttpStatus())
                .body(ErrorResponse.createErrorResponse(BIND_ERROR, request.getRequestURI()));
    }
}
