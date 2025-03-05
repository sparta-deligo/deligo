package com.example.deligo.common.jwt;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // @CheckAuth 어노테이션이 없는 경우 -> JWT 검증 없이 통과
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Method method = handlerMethod.getMethod();
        boolean isCheckAuthRequired = method.isAnnotationPresent(CheckAuth.class) ||
                handlerMethod.getBeanType().isAnnotationPresent(CheckAuth.class);

        if (!isCheckAuthRequired) {
            return true; // 검증 없이 진행
        }

        // JWT 검증 수행
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException(ExceptionType.TOKEN_MISSING);
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new CustomException(ExceptionType.TOKEN_INVALID);
        }

        Long userId = jwtUtil.getUserIdFromToken(token);
        request.setAttribute("userId", userId);

        return true; // 컨트롤러 메서드 실행 허용
    }
}