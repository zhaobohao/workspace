package com.gitee.easyopen.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.gitee.easyopen.message.Errors;

/**
 * @author tanghc
 */
public class RequestUtil {

    private static final String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_TEXT = "text/plain";

    private static final String UTF8 = "UTF-8";
    private static final String GET = "get";

    private static final String UNKOWN = "unknown";
    private static final String LOCAL_IP = "127.0.0.1";
    private static final int IP_LEN = 15;

    public static String getText(HttpServletRequest request) throws Exception {
        return IOUtils.toString(request.getInputStream(), UTF8);
    }

    /**
     * 从request中获取json。如果提交方式是application/x-www-form-urlencoded，则组装成json格式。
     * 
     * @param request request对象
     * @return 返回json
     * @throws IOException
     */
    public static String getJson(HttpServletRequest request) throws Exception {
        String requestJson = null;
        String contectType = request.getContentType();
        if (contectType == null || "".equals(contectType.trim())) {
            throw Errors.NO_CONTECT_TYPE_SUPPORT.getException(contectType);
        }

        contectType = contectType.toLowerCase();

        if (contectType.contains(CONTENT_TYPE_JSON) || contectType.contains(CONTENT_TYPE_TEXT)) {
            requestJson = getText(request);
        } else if (contectType.contains(CONTENT_TYPE_URLENCODED)) {
            Map<String, Object> params = convertRequestParamsToMap(request);
            requestJson = JSON.toJSONString(params);
        } else {
            throw Errors.NO_CONTECT_TYPE_SUPPORT.getException(contectType);
        }
        return requestJson;
    }

    /**
     * request中的参数转换成map
     * 
     * @param request request对象
     * @return 返回参数键值对
     */
    public static Map<String, Object> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        if(paramMap == null || paramMap.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Object> retMap = new HashMap<String, Object>(paramMap.size());

        Set<Entry<String, String[]>> entrySet = paramMap.entrySet();

        for (Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            if (values.length == 1) {
                retMap.put(name, values[0]);
            } else if (values.length > 1) {
                retMap.put(name, values);
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }

    /**
     * 获取客户端真实IP
     * 
     * @param request request对象
     * @return 返回ip
     */
    public static String getClientIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || UNKOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCAL_IP.equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                try {
                    InetAddress inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    // ignore
                }
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > IP_LEN) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;

    }
    
    /**
     * 是否是get请求
     * @param request request对象
     * @return true，是
     */
    public static boolean isGetRequest(HttpServletRequest request) {
        return GET.equalsIgnoreCase(request.getMethod());
    }

}
