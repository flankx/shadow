package com.github.shadow.aop;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.shadow.util.JsonUtils;

@Aspect
@Component
public class RequestLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

    @Pointcut("execution(!static com.github.shadow.pojo.R *(..)) "
        + "&& (@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController))")
    private void pointcut() {}

    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =
            (requestAttributes == null) ? null : ((ServletRequestAttributes)requestAttributes).getRequest();
        String requestUrl = Objects.requireNonNull(request).getRequestURI();
        String requestMethod = request.getMethod();
        Object[] args = pjp.getArgs();
        logger.info("===Request=== {}: {}; params:{}", requestMethod, requestUrl, args);
        try {
            Object retVal = pjp.proceed(pjp.getArgs());
            logger.info("===Result===  {}", JsonUtils.toJsonString(retVal));
            return retVal;
        } finally {
            stopWatch.stop();
            logger.info("<=== {}: {} ({} ms)", requestMethod, requestUrl, stopWatch.getTotalTimeMillis());
        }

    }
}
