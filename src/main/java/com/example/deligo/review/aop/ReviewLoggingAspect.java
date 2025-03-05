package com.example.deligo.review.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
public class ReviewLoggingAspect {
    @Before("execution(* com.example.deligo.review.service.ReviewService.createReview(..))")
    public void logBeforeCreateReview(JoinPoint joinPoint) {
        log.info("[리뷰 생성 요청] - 메서드: {} | 파라미터: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @Before("execution(* com.example.deligo.review.service.ReviewService.updateReview(..))")
    public void logBeforeUpdateReview(JoinPoint joinPoint) {
        log.info("[리뷰 수정 요청] - 메서드: {} | 파라미터: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @Before("execution(* com.example.deligo.review.service.ReviewService.deleteReview(..))")
    public void logBeforeDeleteReview(JoinPoint joinPoint) {
        log.info("[리뷰 삭제 요청] - 메서드: {} | 파라미터: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @Before("execution(* com.example.deligo.review.service.ReviewService.getReviewsByStore(..))")
    public void logBeforeGetReviewsByStore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long storeId = (args.length > 0) && args[0] instanceof Long ? (Long) args[0] : null;
        log.info("[리뷰 조회 요청] - 메서드: {} | 가게: {}", joinPoint.getSignature(), storeId);
    }

    @AfterReturning(pointcut = "execution(* com.example.deligo.review.service.ReviewService.createReview(..))", returning = "result")
    public void logAfterCreateReview(JoinPoint joinPoint, Object result) {
        log.info("[리뷰 생성 완료] - 메서드: {} | 결과: {}", joinPoint.getSignature(), result);
    }

    @AfterReturning(pointcut = "execution(* com.example.deligo.review.service.ReviewService.updateReview(..))",returning = "result")
    public void logAfterUpdateReview(JoinPoint joinPoint, Object result) {
        log.info("[리뷰 수정 완료] - 메서드: {} | 결과: {}", joinPoint.getSignature(),result);
    }

    @AfterReturning(pointcut = "execution(* com.example.deligo.review.service.ReviewService.deleteReview(..))")
    public void logAfterDeleteReview(JoinPoint joinPoint) {
        log.info("[리뷰 삭제 완료] - 메서드: {}", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "execution(* com.example.deligo.review.service.ReviewService.getReviewsByStore(..))", returning = "result")
    public void logAfterGetReviewsByStore(JoinPoint joinPoint, Object result) {
        Long resultSize = (result instanceof List) ? ((List<?>) result).size() :
                (result instanceof org.springframework.data.domain.Page) ? ((org.springframework.data.domain.Page<?>) result).getTotalElements() : -1;
        log.info("[리뷰 조회 완료] - 메서드: {} | 결과 개수: {}", joinPoint.getSignature(), resultSize);
    }

    @AfterThrowing(pointcut = "execution(* com.example.deligo.review.service.ReviewService.*(..))",throwing = "exception")
    public void logExceptionInReviewService(JoinPoint joinPoint, Exception exception) {
        Object[] args = joinPoint.getArgs();
        log.error("[리뷰 처리 중 예외 발생] - 메서드: {} | 예외: {}", joinPoint.getSignature(), exception.getMessage(), exception);
    }
}