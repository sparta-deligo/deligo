package com.example.deligo.common.jwt;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {
    // JwtUtil과 UserRepository는 의존성 주입을 통해 가져옴
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private static final List<String> EXCLUDED_URIS = List.of(
            "/users/signup",
            "/users/login"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if(isExcludedUri(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String authHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new CustomException(ExceptionType.TOKEN_MISSING, "토큰이 존재하지 않습니다.");
            }

            String token = authHeader.substring(7);

            if (!jwtUtil.validateToken(token)) {
                throw new CustomException(ExceptionType.TOKEN_INVALID, "유효하지 않은 토큰입니다.");
            }

            Long userId = jwtUtil.getUserIdFromToken(token);

            boolean userExists = userRepository.existsById(userId);
            if (!userExists) {
                throw new CustomException(ExceptionType.USER_NOT_FOUND, "토큰에 해당하는 사용자가 존재하지 않습니다.");
            }

            request.setAttribute("userId", userId);

            chain.doFilter(request, response);

        } catch (CustomException e) {
            handleException(e, (HttpServletResponse) response);
        }
    }

    private void handleException(CustomException e, HttpServletResponse response) throws IOException {
        response.setStatus(e.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = Map.of(
                "type", e.getExceptionType(),
                "message", e.getMessage()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private boolean isExcludedUri(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return EXCLUDED_URIS.contains(requestUri);
    }
}