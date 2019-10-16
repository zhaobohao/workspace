package com.gitee.sop.gatewaycommon.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author tanghc
 */
public class ResponseUtil {
    private ResponseUtil(){}

    public static final String UTF_8 = "UTF-8";

    private static Logger log = LoggerFactory.getLogger(ResponseUtil.class);

    public static void writeJson(HttpServletResponse response, Object result) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding(UTF_8);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            log.error("doWriter", e);
        }
    }

    /**
     * map转成xml
     *
     * @param parameters
     * @return 返回xml内容
     */
    public static String mapToXml(JSONObject parameters) {
        String content = doMap2xml(parameters);
        return content;
    }

    private static String doMap2xml(JSONObject parameters) {
        StringBuilder sb = new StringBuilder();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (v instanceof JSONObject) {
                sb.append("<").append(k).append(">")
                        .append(doMap2xml((JSONObject) v))
                        .append("</").append(k).append(">");
            } else if (v instanceof JSONArray) {
                JSONArray collection = (JSONArray) v;
                String items = buildItems(k + "_item", collection);
                sb.append("<").append(k).append(">")
                        .append(items)
                        .append("</").append(k).append(">");
            } else {
                sb.append("<").append(k).append("><![CDATA[")
                        .append(v)
                        .append("]]></").append(k).append(">");
            }
        }
        return sb.toString();
    }

    private static String buildItems(String key, JSONArray collection) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < collection.size(); i++) {
            Object jsonObject = collection.get(i);
            sb.append("<").append(key).append(">");
            if (jsonObject instanceof JSONObject) {
                sb.append(doMap2xml((JSONObject) jsonObject));
            } else {
                sb.append(jsonObject);
            }
            sb.append("</").append(key).append(">");
        }
        return sb.toString();
    }

    @Data
    public static class Persion {
        int id;
        String name;
        List<String> items = Arrays.asList("item1", "item2");
        List<Man> child = Arrays.asList(new Man("Jim"), new Man("Tom"));
    }

    @Data
    public static class Man{
        String name;

        public Man(String name) {
            this.name = name;
        }
    }
}
