package com.example.modifying_phase_service.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.modifyingphase.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        log.info(">> Entering: {}", methodName);

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            log.info("<< Exiting: {} | Duration: {}ms", methodName, duration);
            return result;
        } catch (Exception ex) {
            log.error("!! Exception in: {} | Message: {}", methodName, ex.getMessage());
            throw ex;
        }
    }

    @Around("execution(* com.modifyingphase.controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("[REQUEST] {}", methodName);
        Object result = joinPoint.proceed();
        log.info("[RESPONSE] {} completed", methodName);
        return result;
    }
}