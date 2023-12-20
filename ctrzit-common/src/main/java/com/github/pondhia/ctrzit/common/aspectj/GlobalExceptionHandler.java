package com.github.pondhia.ctrzit.common.aspectj;


import com.github.pondhia.ctrzit.common.constant.BusinessStatus;
import com.github.pondhia.ctrzit.common.domain.resp.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Global Exception Handler
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle missing request parameter exception
     *
     * @param e exception
     * @return response result
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, MissingRequestHeaderException.class})
    public R<Void> handleMissingServletRequestParameterException(Exception e) {
        log.error(e.getMessage(), e);
        return R.fail(BusinessStatus.PARAMETER_MISSING, e.getMessage());
    }

    /**
     * Handle http message not readable exception
     *
     * @param e exception
     * @return response result
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return R.fail(BusinessStatus.REQUEST_RESOLUTION_FAILED, e.getMessage());
    }


    /**
     * Handle method argument not valid exception
     *
     * @param e exception
     * @return response result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .map(item -> "[" + item.getField() + "]" + item.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error(e.getMessage(), e);
        return R.fail(BusinessStatus.PARAMETER_VERIFICATION_FAILED, errorMessage);
    }

    /**
     * Handle other exception
     *
     * @param e exception
     * @return response result
     */
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.fail(BusinessStatus.FAIL, e.getMessage());
    }
}
