package com.ssafy.goumunity.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class PerformanceInterceptor implements HandlerInterceptor {

    private ThreadLocal<Long> perfTime = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        perfTime.set(System.currentTimeMillis());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView)
            throws Exception {
        long last = System.currentTimeMillis();
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        Long startTime = perfTime.get();
        log.info("[{}{}]  cost {}ms", request.getMethod(), request.getRequestURI(), last - startTime);
        perfTime.remove();
    }
}
