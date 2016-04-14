package com.unaimasa.marvelcomics.util;

import com.unaimasa.marvelcomics.Constants;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by unai.masa on 13/04/2016.
 */
public class StringUtil {
    private static final String INNER_STUB_PATTERN = "@STUB@";

    public static String format(String source, String pattern, String... params) {
        if (params == null || params.length == 0) {
            return source;
        }
        source = source.replace(pattern, INNER_STUB_PATTERN);
        if (StringUtils.countMatches(source, INNER_STUB_PATTERN) == params.length) {
            for (String val : params) {
                source = source.replaceFirst(INNER_STUB_PATTERN, val);
            }
        } else {
            throw new IllegalArgumentException("Source string doesn't match pattern and params");
        }
        return source;
    }

    public static String format(String source, String... params) {
        return format(source, Constants.ENDPOINT_PARAM_TEMPLATE, params);
    }
}
