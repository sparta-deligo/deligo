package com.example.deligo.common.jwt;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
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
            log.error("토큰 만료: {}", token);
            throw new CustomException(ExceptionType.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error("유효하지 않은 토큰: {}", token);
            throw new CustomException(ExceptionType.TOKEN_INVALID);
        } catch (SignatureException e) {
            log.error("토큰 서명 오류: {}", token);
            throw new CustomException(ExceptionType.TOKEN_SIGNATURE_INVALID);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("토큰 만료: {}", token);
            throw new CustomException(ExceptionType.TOKEN_EXPIRED);
        } catch (SignatureException e) {
            log.error("토큰 서명 오류: {}", token);
            throw new CustomException(ExceptionType.TOKEN_SIGNATURE_INVALID);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("유효하지 않은 토큰: {}", token);
            throw new CustomException(ExceptionType.TOKEN_INVALID);
        }
    }
}