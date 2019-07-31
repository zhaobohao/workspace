package com.gitee.durcframework.easyopen;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PostDemoTest extends TestCase {
    
    @Test
    public void testPost() throws IOException {
        String appKey = "test";
        String secret = "123456";
        // 业务参数
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("goodsName", "iphoneX");

        String json = JSON.toJSONString(jsonMap);
        json = URLEncoder.encode(json, "utf-8");

        // 系统参数
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", "goods.get");
        param.put("app_key", appKey);
        param.put("data", json);
        param.put("timestamp", getTime());
        param.put("version", "");

        String sign = buildSign(param, secret);

        param.put("sign", sign);

        System.out.println("=====请求数据=====");
        System.out.println(JSON.toJSON(param));
    }
    
    /**
     * 构建签名串
     * @param paramsMap 请求参数
     * @param secret 秘钥
     * @return
     * @throws IOException
     */
    public String buildSign(Map<String, ?> paramsMap, String secret) throws IOException {
        Set<String> keySet = paramsMap.keySet();
        List<String> paramNames = new ArrayList<String>(keySet);

        Collections.sort(paramNames);

        StringBuilder paramNameValue = new StringBuilder();

        for (String paramName : paramNames) {
            paramNameValue.append(paramName).append(paramsMap.get(paramName));
        }

        String source = secret + paramNameValue.toString() + secret;

        return md5(source);
    }

    /**
     * 生成md5,全部大写。
     * 
     * @param source
     * @return
     */
    public static String md5(String source) {
        return DigestUtils.md5Hex(source).toUpperCase();
    }

    public String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
