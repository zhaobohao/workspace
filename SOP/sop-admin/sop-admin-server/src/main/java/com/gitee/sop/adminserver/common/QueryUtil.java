package com.gitee.sop.adminserver.common;

import com.gitee.easyopen.verify.DefaultMd5Verifier;
import com.gitee.sop.adminserver.bean.HttpTool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanghc
 */
public class QueryUtil {

    private static HttpTool httpTool = new HttpTool();

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

    public static String requestServer(String ipPort, String path, String secret) throws Exception {
        DefaultMd5Verifier md5Verifier = new DefaultMd5Verifier();
        Map<String, Object> params = new HashMap<>(16);
        params.put("time", System.currentTimeMillis());
        String sign = md5Verifier.buildSign(params, secret);
        params.put("sign", sign);
        String query = QueryUtil.buildQueryString(params);
        path = path.startsWith("/") ? path.substring(1) : path;
        String url = "http://" + ipPort + "/" + path + "?" + query;
        return httpTool.get(url, null);
    }
}
