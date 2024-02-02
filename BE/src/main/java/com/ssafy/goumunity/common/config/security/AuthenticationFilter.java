package com.ssafy.goumunity.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.constraint.ConstraintsValidators;
import com.ssafy.goumunity.common.exception.ErrorResponse;
import com.ssafy.goumunity.domain.user.controller.request.UserLoginRequest;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final String SESSION_LOGIN_USER_KEY;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UserLoginRequest dto = null;
        try {
            dto = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!ConstraintsValidators.validateLoginPattern(dto)) {
            throw new AuthenticationFailureException(UserErrorCode.LOGIN_FAILED);
        }

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPassword(), new ArrayList<>()));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException, ServletException {

        CustomDetails principal = (CustomDetails) authResult.getPrincipal();
        request.getSession().setAttribute(SESSION_LOGIN_USER_KEY, principal.getUser());

        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        log.error("unsuccessfulAuthentication", failed);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=utf-8");
        ErrorResponse errorResponse =
                ErrorResponse.createErrorResponse(
                        ((AuthenticationFailureException) failed).getErrorCode(), request.getRequestURI());
        String errorMsg = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(errorMsg);
    }
}
