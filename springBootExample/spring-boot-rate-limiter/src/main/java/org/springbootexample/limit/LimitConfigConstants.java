package org.springbootexample.limit;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface LimitConfigConstants {
    public static final byte LIMIT_STATUS_OPEN = 1;
    public static final byte LIMIT_STATUS_CLOSE = 0;
    public static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;
    public static final String UTF8 = "UTF-8";
    public static final String FORMAT_JSON = "json";
    public static final String DEFAULT_SIGN_METHOD = "md5";
    public static final String EMPTY_JSON = "{}";
}
