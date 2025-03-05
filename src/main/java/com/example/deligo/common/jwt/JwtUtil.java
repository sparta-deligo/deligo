package com.example.deligo.common.jwt;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간
    private Key signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Key getSigningKey() {
        return signingKey;
    }

    public Map<String, String> generateToken(Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String token = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return Map.of("token", token, "expiresAt", String.valueOf(expiryDate.getTime()));
    }

    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Long.parseLong(claims.getSubject());
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰: {}", e.getClaims().getExpiration());
            throw new CustomException(ExceptionType.TOKEN_EXPIRED, "토큰이 만료되었습니다.");
        } catch (JwtException e) {
            log.error("JWT 오류: {}", token);
            throw new CustomException(ExceptionType.TOKEN_INVALID, "JWT가 유효하지 않습니다.");
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("토큰 만료: {}", token);
            return false; // 만료된 토큰은 false 반환
        } catch (JwtException e) {
            log.error("JWT 검증 실패: {}", token);
            throw new CustomException(ExceptionType.TOKEN_INVALID, "토큰이 유효하지 않습니다.");
        }
    }
}