package com.denachina.shadow.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ShadowLogger {
    private static final Logger logger = LoggerFactory.getLogger(ShadowLogger.class);

    @Pointcut("execution(public * com.denachina.shadow.service.impl.*.*(..))")
    private void pointcut(){}

    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable{
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().getName();
        Object[] args = pjp.getArgs();

        logger.info("==== {}:{} START, with params:{} ====", className, methodName, args);

        Object retVal = pjp.proceed(pjp.getArgs());

        logger.info("==== {}:{} END, with return value:{} ====", className, methodName, retVal);

        return retVal;
    }
}
