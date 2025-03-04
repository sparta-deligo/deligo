package com.example.deligo.common.jwt;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException(ExceptionType.UNAUTHORIZED, "토큰이 필요합니다.");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new CustomException(ExceptionType.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }

        Long userId = jwtUtil.getUserIdFromToken(token);
        request.setAttribute("userId", userId);

        chain.doFilter(request, response);
    }
}