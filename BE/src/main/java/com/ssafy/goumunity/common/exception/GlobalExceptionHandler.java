package com.ssafy.goumunity.common.exception;

import static com.ssafy.goumunity.common.exception.GlobalErrorCode.*;

import jakarta.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            CustomException e, HttpServletRequest request) {
        log.debug("Custom Error 발생", e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.createErrorResponse(e.getErrorCode(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            HttpServletRequest request) {
        return ResponseEntity.status(BIND_ERROR.getHttpStatus())
                .body(ErrorResponse.createErrorResponse(BIND_ERROR, request.getRequestURI()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            HttpServletRequest request) {
        return ResponseEntity.status(REQUIRED_PARAM_NOT_FOUND.getHttpStatus())
                .body(ErrorResponse.createErrorResponse(REQUIRED_PARAM_NOT_FOUND, request.getRequestURI()));
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ErrorResponse> handleCannotCreateTransactionException(
            HttpServletRequest request, CannotCreateTransactionException e) {
        log.debug("CannotCreateTransactionException Error 발생", e);
        return ResponseEntity.status(DB_CONNECT_FAIL.getHttpStatus())
                .body(ErrorResponse.createErrorResponse(DB_CONNECT_FAIL, request.getRequestURI()));
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorResponse> handleConnectException(
            HttpServletRequest request, ConnectException e) {
        log.debug("ConnectException Error 발생", e);
        return ResponseEntity.status(DB_CONNECT_FAIL.getHttpStatus())
                .body(ErrorResponse.createErrorResponse(DB_CONNECT_FAIL, request.getRequestURI()));
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleFileSizeLimitExceededException(
            HttpServletRequest request, FileSizeLimitExceededException e) {
        log.debug("FileSizeLimitExceededException Error 발생", e);
        return ResponseEntity.status(FILE_SIZE_LIMIT_EXCEEDED.getHttpStatus())
                .body(ErrorResponse.createErrorResponse(FILE_SIZE_LIMIT_EXCEEDED, request.getRequestURI()));
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleSizeLimitExceededException(
            HttpServletRequest request, SizeLimitExceededException e) {
        log.debug("SizeLimitExceededException Error 발생", e);
        return ResponseEntity.status(FILE_SIZE_LIMIT_EXCEEDED.getHttpStatus())
                .body(ErrorResponse.createErrorResponse(FILE_SIZE_LIMIT_EXCEEDED, request.getRequestURI()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(
            HttpServletRequest request, MaxUploadSizeExceededException e) {
        log.debug("MaxUploadSizeExceededException Error 발생", e);
        return ResponseEntity.status(FILE_SIZE_LIMIT_EXCEEDED.getHttpStatus())
                .body(ErrorResponse.createErrorResponse(FILE_SIZE_LIMIT_EXCEEDED, request.getRequestURI()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            HttpServletRequest request, AccessDeniedException e) {
        log.debug("AccessDeniedException Error 발생", e);
        return ResponseEntity.status(FORBIDDEN.getHttpStatus())
                .body(ErrorResponse.createErrorResponse(FORBIDDEN, request.getRequestURI()));
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(
            InternalServerException e, HttpServletRequest request) {
        log.info("서버 내부 예외 발생!! 발생 위치 : {}\n", e.getFrom(), e.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.createErrorResponse(
                                GlobalErrorCode.INTERNAL_SERVER_ERROR_CODE, request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(
            Exception e, HttpServletRequest request) {
        log.warn("Unhandled Exception 발생!!", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.createErrorResponse(
                                GlobalErrorCode.INTERNAL_SERVER_ERROR_CODE, request.getRequestURI()));
    }
}
