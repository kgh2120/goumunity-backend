package com.ssafy.goumunity.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.user.dto.UserLoginDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        UserLoginDto dto = null;
        try {
            dto = objectMapper.readValue(request.getInputStream(), UserLoginDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("dto : {}", dto);
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
}
