package com.github.pondhia.ctrzit.common.utils;

import java.util.*;

/**
 * String Utils
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * Empty string
     */
    private static final String NULL_STR = "";

    /**
     * Get value or default value
     *
     * @param value        value
     * @param defaultValue default value
     * @param <T>          type
     * @return value or default value
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * Check if the string is blank
     *
     * @param coll collection
     * @return true: blank, false: not blank
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * Check if the collection is not empty
     *
     * @param coll collection
     * @return true: not empty, false: empty
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * Check if the object array is empty
     *
     * @param objects object array
     * @return true: empty, false: not empty
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * Check if the object array is not empty
     *
     * @param objects object array
     * @return true: not empty, false: empty
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * Check if the map is empty
     *
     * @param map map
     * @return true: empty, false: not empty
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * Check if the map is not empty
     *
     * @param map map
     * @return true: not empty, false: empty
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Check if the string is empty
     *
     * @param str String
     * @return true: empty, false: not empty
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULL_STR.equals(str.trim());
    }

    /**
     * Check if the string is not empty
     *
     * @param str String
     * @return true: not empty, false: empty
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Check if the object is null
     *
     * @param object Object
     * @return true: null, false: not null
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Check if the object is not null
     *
     * @param object Object
     * @return true: not null, false: null
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * Check if the object is an array (basic type)
     *
     * @param object object
     * @return true: is array, false: not array
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * Trim
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * Substring
     *
     * @param str   source string
     * @param start start position
     * @return result
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return NULL_STR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return NULL_STR;
        }

        return str.substring(start);
    }

    /**
     * Substring
     *
     * @param str   source string
     * @param start start position
     * @param end   end position
     * @return result
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULL_STR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULL_STR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * String to Set
     *
     * @param str source string
     * @param sep separator
     * @return set
     */
    public static Set<String> str2Set(String str, String sep) {
        return new HashSet<>(str2List(str, sep, true, false));
    }

    /**
     * Convert String to List
     *
     * @param str         source string
     * @param sep         separator
     * @param filterBlank filter blank string
     * @param trim        remove blank
     * @return list
     */
    public static List<String> str2List(String str, String sep, boolean filterBlank, boolean trim) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(str)) {
            return list;
        }

        // filter blank string
        if (filterBlank && StringUtils.isBlank(str)) {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split) {
            if (filterBlank && StringUtils.isBlank(string)) {
                continue;
            }
            if (trim) {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

}