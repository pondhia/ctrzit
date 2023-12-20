package com.github.pondhia.ctrzit.common.interceptor;


import com.github.pondhia.ctrzit.common.utils.StringUtils;
import com.github.pondhia.ctrzit.common.utils.uuid.IdUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Log Interceptor
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    public static final String TRACE_ID = "TRACE_ID";

    @Override
    public boolean preHandle(@NonNull HttpServletRequest httpServletRequest,
                             @NonNull HttpServletResponse httpServletResponse,
                             @NonNull Object object) {
        if (StringUtils.isEmpty(MDC.get(TRACE_ID))) {
            MDC.put(TRACE_ID, IdUtils.fastSimpleUUID());
        }
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest httpServletRequest,
                           @NonNull HttpServletResponse httpServletResponse,
                           @NonNull Object object, ModelAndView modelAndView) {
        // remove trace id
        MDC.remove(TRACE_ID);
    }
}