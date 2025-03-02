package com.example.deligo.review.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ReviewLoggingAspect {
    @AfterReturning(pointcut = "execution(* com.example.deligo.service.ReviewService.createReview(..))",returning = "review")
    public void logCreateReview(Object review) {
        log.info("새로운 리뷰가 생성되었습니다. {}", review);
    }
}