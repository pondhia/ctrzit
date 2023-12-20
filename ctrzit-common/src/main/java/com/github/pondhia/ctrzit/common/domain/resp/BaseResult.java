package com.github.pondhia.ctrzit.common.domain.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * Base Response Result
 *
 * @param <T> data type
 */
@Data
public abstract class BaseResult<T> implements Serializable {
    /**
     * Business status code
     */
    private int code;
    /**
     * Response data
     */
    private T data;
    /**
     * Response message
     */
    private String message;
    /**
     * Response timestamp
     */
    private Long timestamp;
    /**
     * Trace ID
     */
    private String traceId;
}
