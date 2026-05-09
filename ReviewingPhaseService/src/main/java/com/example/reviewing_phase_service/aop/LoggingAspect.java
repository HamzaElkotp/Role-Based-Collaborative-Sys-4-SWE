package com.example.reviewing_phase_service.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.example.reviewingphaseservice.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("➡ Calling: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(
        pointcut = "execution(* com.example.reviewingphaseservice.service.*.*(..))",
        returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("✅ Completed: {} | Result: {}",
                joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(
        pointcut = "execution(* com.example.reviewingphaseservice.service.*.*(..))",
        throwing = "ex"
    )
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("❌ Exception in: {} | Message: {}",
                joinPoint.getSignature().getName(), ex.getMessage());
    }

    @Around("execution(* com.example.reviewingphaseservice.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        log.info("⏱ [{}] executed in {} ms",
                joinPoint.getSignature().getName(), duration);
        return result;
    }
}
