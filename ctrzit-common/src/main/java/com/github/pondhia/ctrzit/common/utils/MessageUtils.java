package com.github.pondhia.ctrzit.common.utils;

import com.github.pondhia.ctrzit.common.utils.spring.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Message Utils
 */
public class MessageUtils {
    /**
     * According to the message key and parameters to get the message
     *
     * @param key  message key
     * @param args arguments
     * @return internationalized message
     */
    public static String message(String key, Object... args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}
