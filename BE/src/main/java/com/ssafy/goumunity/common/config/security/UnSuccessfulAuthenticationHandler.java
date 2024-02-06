package com.ssafy.goumunity.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.ErrorResponse;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
@Slf4j
public class UnSuccessfulAuthenticationHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=utf-8");
        ErrorResponse errorResponse =
                ErrorResponse.createErrorResponse(UserErrorCode.LOGIN_FAILED, request.getRequestURI());
        String errorMsg = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(errorMsg);
    }
}
