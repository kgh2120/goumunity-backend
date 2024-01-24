package com.ssafy.goumunity.common.exception;

import static com.ssafy.goumunity.common.exception.CustomErrorCode.INTERNAL_SERVER_ERROR_CODE;
import static com.ssafy.goumunity.common.exception.GlobalErrorCode.BIND_ERROR;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            CustomException e, HttpServletRequest request) {
        log.debug("Error 발생!!", e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.createErrorResponse(e.getErrorCode(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            HttpServletRequest request) {
        return ResponseEntity.status(BIND_ERROR.getHttpStatus())
                .body(ErrorResponse.createErrorResponse(BIND_ERROR, request.getRequestURI()));
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(
            InternalServerException e, HttpServletRequest request) {
        log.info("서버 내부 예외 발생!! 발생 위치 : {}\n", e.getFrom(), e.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.createErrorResponse(INTERNAL_SERVER_ERROR_CODE, request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(
            Exception e, HttpServletRequest request) {
        log.warn("Unhandled Exception 발생!!", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.createErrorResponse(INTERNAL_SERVER_ERROR_CODE, request.getRequestURI()));
    }
}
