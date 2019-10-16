package com.gitee.sop.adminserver.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author tanghc
 */
public class QueryUtil {

    public static String buildQueryString(Map<String, ?> params) throws UnsupportedEncodingException {
        if (params == null || params.size() == 0) {
            return "";
        }
        StringBuilder query = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, ?> entry : params.entrySet()) {
            String name = entry.getKey();
            String value = String.valueOf(entry.getValue());
            if (i++ > 0) {
                query.append("&");
            }
            query.append(name).append("=").append(URLEncoder.encode(value, "UTF-8"));
        }
        return query.toString();
    }
}
