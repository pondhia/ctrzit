package com.github.pondhia.ctrzit.common.aspectj;

import com.alibaba.fastjson2.JSON;
import com.github.pondhia.ctrzit.common.domain.resp.R;
import com.github.pondhia.ctrzit.common.interceptor.LogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class WebLogAspect {

    @Pointcut("execution(public * com.github.pondhia.ctrzit..*Controller.*(..)) ")
    private void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();

        // request info
        log.debug("=".repeat(42) + " Request Info " + "=".repeat(42));
        log.debug("[HTTP Request] Address (URL): {}", request.getRequestURI());
        log.debug("[HTTP Request] HTTP Request Method: {}", request.getMethod());
        log.debug("[HTTP Request] Java Method: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.debug("[HTTP Request] IP: {}", request.getRemoteAddr());
        log.debug("[HTTP Request] Parameters: {}", JSON.toJSONString(joinPoint.getArgs()));
        log.debug("=".repeat(42) + " Request Info " + "=".repeat(42));


    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // processing only common response
        Object object = proceedingJoinPoint.proceed();
        if (!(object instanceof R<?> result)) {
            return object;
        }
        long startTime = System.currentTimeMillis();
        result.setTraceId(MDC.get(LogInterceptor.TRACE_ID));

        // response info
        log.debug("=".repeat(42) + " Response Info " + "=".repeat(42));
        log.debug("[HTTP Response] Parameters: {}", JSON.toJSONString(result));
        log.debug("[HTTP Response] Used time: {}ms", System.currentTimeMillis() - startTime);
        log.debug("=".repeat(42) + " Response Info " + "=".repeat(42));

        return result;
    }
}