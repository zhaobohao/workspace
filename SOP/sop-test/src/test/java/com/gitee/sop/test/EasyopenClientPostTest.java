package com.gitee.sop.test;

import com.alibaba.fastjson.JSON;
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

/**
 * @author tanghc
 */
public class EasyopenClientPostTest extends TestBase {

    String url = "http://localhost:8081";
    String appKey = "easyopen_test";
    String secret = "G9w0BAQEFAAOCAQ8AMIIBCgKCA";

    @Test
    public void testPost() throws IOException {
        // 业务参数
        Map<String, String> bizParams = new HashMap<String, String>();
        bizParams.put("goods_name", "iphoneX");

        String json = JSON.toJSONString(bizParams);
        json = URLEncoder.encode(json, "utf-8");

        // 系统参数
        Map<String, String> params = new HashMap<>();
        params.put("name", "easyopen.goods.get");
        params.put("app_key", appKey);
        params.put("data", json);
        params.put("timestamp", getTime());
        params.put("version", "");

        String sign = buildSign(params, secret);

        params.put("sign", sign);

        System.out.println("=====请求数据=====");
        System.out.println(JSON.toJSONString(params));
        System.out.println("=====返回结果=====");
        String resp = post(url, params);
        System.out.println(resp);
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
