package com.ssafy.goumunity.common.config.security;

import static com.ssafy.goumunity.common.exception.GlobalErrorCode.UNAUTHORIZED_ACCESS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@RequiredArgsConstructor
@Slf4j
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        //        log.error("accessdeniedHanddler", accessDeniedException);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=utf-8");
        ErrorResponse errorResponse =
                ErrorResponse.createErrorResponse(UNAUTHORIZED_ACCESS, request.getRequestURI());
        String errorMsg = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(errorMsg);
    }
}
