package com.example.project_service.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // ── Log كل method في الـ service layer ──────────────────────

    @Before("execution(* com.example.projectservice.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("➡ Calling: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(
        pointcut = "execution(* com.example.projectservice.service.*.*(..))",
        returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("✅ Completed: {} | Result: {}",
                joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(
        pointcut = "execution(* com.example.projectservice.service.*.*(..))",
        throwing = "ex"
    )
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("❌ Exception in: {} | Message: {}",
                joinPoint.getSignature().getName(), ex.getMessage());
    }

    // ── قياس وقت تنفيذ كل method ────────────────────────────────
    @Around("execution(* com.example.projectservice.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        log.info("⏱ [{}] executed in {} ms",
                joinPoint.getSignature().getName(), duration);
        return result;
    }
}
