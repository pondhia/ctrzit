package com.github.pondhia.ctrzit.common.domain.resp;


import com.github.pondhia.ctrzit.common.constant.BusinessStatus;
import com.github.pondhia.ctrzit.common.interceptor.LogInterceptor;
import com.github.pondhia.ctrzit.common.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.MDC;

import java.io.Serial;
import java.io.Serializable;

/**
 * Common Response Result
 *
 * @param <T> data type
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class R<T> extends BaseResult<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -1066820459007392829L;

    public static final String DEFAULT_SUCCESS_MESSAGE = "Operation succeeded";

    public static final String DEFAULT_FAIL_MESSAGE = "Operation failed";

    public R() {
    }

    public R(String message) {
        this.setMessage(message);
    }

    public R(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public R(String message, T data) {
        this.setMessage(message);
        this.setData(data);
    }

    public R(int code, String message, T data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }


    public static <T> R<T> ok() {
        return ok(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> R<T> ok(int code, String message) {
        return ok(code, message, null);
    }

    public static <T> R<T> ok(String message) {
        return ok(message, null);
    }

    public static <T> R<T> ok(T data) {
        return ok(DEFAULT_SUCCESS_MESSAGE, data);
    }

    public static <T> R<T> ok(String message, T data) {
        return ok(BusinessStatus.SUCCESS, message, data);
    }

    public static <T> R<T> ok(int code, String message, T data) {
        R<T> result = new R<>(code, message, data);
        result.setTimestamp(DateUtils.getNowDate().getTime());
        result.setTraceId(MDC.get(LogInterceptor.TRACE_ID));
        return result;
    }

    public static <T> R<T> fail() {
        return fail(DEFAULT_FAIL_MESSAGE);
    }

    public static <T> R<T> fail(int code, String message) {
        return fail(code, message, null);
    }

    public static <T> R<T> fail(String message) {
        return fail(message, null);
    }

    public static <T> R<T> fail(T data) {
        return fail(DEFAULT_FAIL_MESSAGE, data);
    }

    public static <T> R<T> fail(String message, T data) {
        return fail(BusinessStatus.FAIL, message, data);
    }

    public static <T> R<T> fail(int code, String message, T data) {
        R<T> result = new R<>(code, message, data);
        result.setTimestamp(DateUtils.getNowDate().getTime());
        result.setTraceId(MDC.get(LogInterceptor.TRACE_ID));
        return result;
    }

    public R<T> addTraceID(String traceId) {
        this.setTraceId(traceId);
        return this;
    }
}